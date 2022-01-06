# BrickPermissions

An extension for [Minestom](https://github.com/Minestom/Minestom) to permissions for players.
Players can have individual permissions or can be assigned to a group with permissions.

## Install

Get the latest jar file from [Github actions](https://github.com/MinestomBrick/BrickPermissions/actions) 
and place it in the extension folder of your minestom server.

## Commands

Don't worry, console can execute all commands ;)

| Command | Permission | Description |
|---|---|---|
| /bp player info (player) | brickpermissions.player.info | Check the combined permissions a player has.|
| /bp player permission add (player) (permission) | brickpermissions.player.permission.add | Add a permission to a player. |
| /bp player permission remove (player) (permission) | brickpermissions.player.permission.remove | Remove a permission from a player. |
| /bp group add (name) | brickpermissions.group.add | Add a new group. |
| /bp group remove (group) | brickpermissions.group.remove | Remove a group. |
| /bp group permission add (group) (permission) | brickpermissions.group.permission.add | Add a permission to a group. |
| /bp group permission remove (group) (permission) | brickpermissions.group.permission.remove | Remove a permission from a group. |
| /bp group info (group) | brickpermissions.group.info | Check all permissions of a group. |
| /bp group list | brickpermissions.group.list | Get a list of all the groups. |

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

