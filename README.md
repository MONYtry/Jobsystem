# Jobsystem

<div align="center">

![Jobsystem Banner](https://img.shields.io/badge/Minecraft-Paper%201.21.8-6B8E23?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk)
![Vault](https://img.shields.io/badge/Economy-Vault-8A2BE2?style=for-the-badge)
![Build](https://img.shields.io/badge/Build-Maven-C71A36?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Alpha-orange?style=for-the-badge)

A lightweight job system plugin for Paper servers, designed for Minecraft 1.21.8.

</div>

## Overview

Jobsystem adds a simple and customizable job progression system to your Minecraft server. Players can choose jobs, level up by performing specific actions like mining, digging, and woodcutting, and claim rewards as they progress.

The plugin integrates with Vault for economy support and uses customizable GUIs, rewards, and messages to fit a wide range of server setups.

## Features

- Multiple job types with progression levels
- Economy rewards via Vault
- Job GUI and reward GUI
- Player progression tracking
- Boosters support
- Configurable messages, rewards, and GUI settings
- Built for Paper 1.21.8

## Screenshots

> Placeholder for in-game screenshots and GUI previews.

![Main menu](https://via.placeholder.com/1200x700?text=Jobsystem+Main+GUI)

![Job selection](https://via.placeholder.com/1200x700?text=Job+Selection+GUI)

![Rewards screen](https://via.placeholder.com/1200x700?text=Rewards+GUI)

## Requirements

- Paper 1.21.8+
- Java 21
- Vault
- A compatible economy provider installed through Vault

## Installation

1. Download the latest release from the Releases page.
2. Place the plugin jar in your server's `plugins` folder.
3. Start the server once so the default config files are generated.
4. Configure the plugin files in:
   - `plugins/Jobsystem/config.yml`
   - `plugins/Jobsystem/messages.yml`
   - `plugins/Jobsystem/gui.yml`
   - `plugins/Jobsystem/playerdata.yml`
5. Restart the server.

## Commands

| Command | Description |
| --- | --- |
| `/job` | Opens the main job GUI |

## Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `job.admin` | OP | Administrative access to the plugin |

## Configuration

The plugin includes a set of configuration files to customize rewards, GUI layout, and messages.

### `config.yml`

Defines:
- job settings
- leveling curves
- rewards
- booster setup

### `messages.yml`

Contains all messages sent to players, such as confirmation texts and notifications.

### `gui.yml`

Controls the visual layout of the in-game GUI windows.

## Project Structure

```text
Jobsystem/
├── src/
│   └── main/
│       ├── java/
│       │   └── de/main/jobsystem/
│       │       ├── Listener/
│       │       ├── booster/
│       │       ├── commands/
│       │       ├── gui/
│       │       ├── manager/
│       │       ├── payout/
│       │       └── Jobsystem.java
│       └── resources/
│           ├── config.yml
│           ├── gui.yml
│           ├── messages.yml
│           ├── playerdata.yml
│           └── plugin.yml
├── pom.xml
├── README.md
└── LICENSE (if added later)
```

## Development

### Build

```bash
mvn clean package
```

This will compile the plugin and generate a JAR in the `target/` directory.

### Requirements for development

- Maven
- Java 21
- Paper API 1.21.8

## Roadmap

- Add more jobs and custom actions
- Improve GUI customization
- Add more reward types
- Add admin commands for resetting player data
- Add more statistics and progression tracking

## Contributing

Contributions are welcome. If you want to improve the plugin, feel free to:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a pull request

## Support

If you need help with setup, configuration, or troubleshooting, feel free to open an issue in the repository.

---

<p align="center">
  <sub>Made for Paper 1.21.8 • Jobsystem by <a href="https://github.com/MONYtry">MONY</a></sub>
</p>
