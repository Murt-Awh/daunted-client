async function run() {
	if(require("electron-squirrel-startup")) return;

    const fs = require("fs");

	const Utils = require("./utils");
	const Config = require("./config");

	Utils.init();
	Config.init(Utils.dataDirectory);
	Config.load();

	const { app, BrowserWindow, ipcMain, dialog, shell } = require("electron");

	ipcMain.on("disableUpdates", async() => {
		Config.data.autoUpdate = false;
		Config.save();
	});

	const path = require("path");
	const msmc = require("msmc");
	const hastebin = require("hastebin");

	let window;
	let canQuit = false;

	function createWindow() {
		let options = {
			width: 800,
			height: 650,
			icon: __dirname + "/assets/icon.png",
			webPreferences: {
				preload: path.join(__dirname, "app.js")
			},
			title: "Daunted Client " + Utils.version,
			show: false,
			backgroundColor: "#000",
			darkTheme: true
		};

		if(Utils.getOsName() == "osx") {
			options.titleBarStyle = "hidden";
		}

		window = new BrowserWindow(options);

		window.loadFile("app.html");
		window.setMenu(null);

		if(process.env.DEVTOOLS) {
			window.webContents.openDevTools();
		}

		window.on("close", (event) => {
			if(!canQuit) {
				event.preventDefault();
				window.webContents.send("close");
			}
		});

		window.once("ready-to-show", () => window.show());

		ipcMain.on("directory", async(event, title, id) => {
			let result = await dialog.showOpenDialog(window,
				{
					title: title,
					properties: ["openDirectory" ]
				}
			);

			let file = result.filePaths[0];

			if(!result.canceled && file) {
				event.sender.send("directory", file, id);
			}
		});

		ipcMain.on("jreError", async(event) => {
			dialog.showMessageBoxSync(window, {
				title: "Invalid Directory",
				message: "JRE must include bin folder."
			});
		});

		ipcMain.on("skinFile", async(event) => {
			let result = await dialog.showOpenDialog(window,
				{
					title: "Select Skin File",
					filters: [
						{
							name: "Minecraft Skins",
							extensions: ["png"]
						},
						{
							name: "All Files",
							extensions: ["*"]
						}
					]
				}
			);

			let file = result.filePaths[0];

			if(!result.canceled && file) {
				event.sender.send("skinFile", file);
			}
		});
	}

	ipcMain.on("msa", async(event) => {
		msmc.fastLaunch("electron", () => {})
				.then(async(result) => {
					await window.webContents.session.clearStorageData();
					event.sender.send("msa", JSON.stringify(result));
				});
	});

	ipcMain.on("crash", async(_event, report, file) => {
		let option = dialog.showMessageBoxSync(window, {
			title: "Game Crashed",
			message: `The game crashed, did you smoke a little too hard?
			Send the log file to fwanchan (murt).`,
			type: "question",
			buttons: [
				"Do Nothing",
				"Open log file"
			]
		});

		if(option == 1) {
			shell.openPath(file);
		}
		if(option != 2) {
			return;
		}
	});

	ipcMain.on("devtools", () => window.webContents.openDevTools());

	ipcMain.on("quit", (event, result) => {
		if(result) {
			canQuit = true;
			app.quit();
		}
		else {
			if(dialog.showMessageBoxSync(window, {
						title: "Quit Launcher?",
						message: "Currently, leaving the launcher means closing the game.",
						type: "question",
						buttons: [
							"Don't quit",
							"Quit game and launcher"
						]
					}) == 1) {
				event.sender.send("quitGame");
			}
		}
	});

	app.whenReady().then(() => {
		createWindow();
	});
}

run();
