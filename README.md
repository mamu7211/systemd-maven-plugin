## Variables

## Template Creation

|Variable|Description|Default|
|---|---|---|
|unit.templateFileLocation| | `ASDF`|

### Variables

|Variable|Description|
|---|---|
|project.groupId| Maven Group ID|
|project.artifactId| |
|project.version| |
|project.description| |           
|user.name||
|user.home||

### Configurable Variables

|Variable|Description|Default|
|---|---|---|
|unit.fileName| Filename of the unit in `/etc/systemd/system` | `${artifactId}.service`|
|unit.description| `[Unit]` directive `Description` | `${project.description}`|
|unit.after| `[Unit]` directive `After` | `network.target`|
|unit.user| `[Unit]` directive `User` | `${user.name}`|
|unit.wantedBy| `[Install]` directive `WantedBy` | `multi-user.target`|
|unit.type| `[Unit]` directive `Type` | `simple`|
|unit.restart| `[Unit]` directive `Restart` | `always`|            
|install.fileName| | `install.sh`|
|install.directory| | `/opt/${project.artifactId}/${project.version}`|
|install.startService| | `ASDF`|            
|run.fileName| | `${install.directory}/${artifact.sh}`|            
|environment.fileName| | `environment.cfg`|
|environment.directory| | `${install.directory}`|
|environment.overwriteInstalled| | `true`|            
|install.overwriteInstalled| | `false`|