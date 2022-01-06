# BrickPermissions

An extension for [Minestom](https://github.com/Minestom/Minestom) to manage the permissions of players.
Players can have individual permissions or can be assigned to a group with permissions. The data can be stored in a 
database server or a local H2 file database.

## Install

Get the latest jar file from [Github actions](https://github.com/MinestomBrick/BrickPermissions/actions) 
and place it in the extension folder of your minestom server.

## Commands

Don't worry, console can execute all commands ;)

| Command | Permission |
|---|---|
| **Players** ||
| /bp player info (player) | brickpermissions.player.info |
| /bp player permission add (player) (permission) | brickpermissions.player.permission.add |
| /bp player permission remove (player) (permission) | brickpermissions.player.permission.remove |
| **Groups** ||
| /bp group add (name) | brickpermissions.group.add |
| /bp group remove (group) | brickpermissions.group.remove |
| /bp group list | brickpermissions.group.list | 
| /bp group info (group) | brickpermissions.group.info |
| /bp group permission add (group) (permission) | brickpermissions.group.permission.add |
| /bp group permission remove (group) (permission) | brickpermissions.group.permission.remove |

## Database

You can change the database settings in the `config.json`.

```json
{
  "database": {
    "dsn": "jdbc:h2:file:./extensions/BrickPermissions/data/database.h2",
    "username": "dbuser",
    "password": "dbuser"
  }
}
```

MySQL is supported, use the following format:

````
"dsn": "jdbc:mysql://<hostname>:<ip>/<database>"
````

## Credits

* The [Minestom](https://github.com/Minestom/Minestom) project

## Contributing

Check our [contributing info](CONTRIBUTING.md)

