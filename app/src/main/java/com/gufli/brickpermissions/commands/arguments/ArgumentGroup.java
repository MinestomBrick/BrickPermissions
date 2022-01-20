package com.gufli.brickpermissions.commands.arguments;

import com.gufli.brickpermissions.PermissionAPI;
import com.gufli.brickpermissions.data.Group;
import net.minestom.server.command.builder.NodeMaker;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArgumentGroup extends Argument<Group> {

    public static final int GROUP_NOT_EXIST = 1;

    public ArgumentGroup(@NotNull String id) {
        super(id);
    }

    @Override
    public @NotNull Group parse(@NotNull String input) throws ArgumentSyntaxException {
        return PermissionAPI.group(input).orElseThrow(() ->
                new ArgumentSyntaxException("Group does not exist.", input, GROUP_NOT_EXIST));
    }

    @Override
    public void processNodes(@NotNull NodeMaker nodeMaker, boolean executable) {
        List<Group> groups = new ArrayList<>(PermissionAPI.groups());

        // Create a primitive array for mapping
        DeclareCommandsPacket.Node[] nodes = new DeclareCommandsPacket.Node[groups.size()];

        // Create a node for each restrictions as literal
        for (int i = 0; i < nodes.length; i++) {
            DeclareCommandsPacket.Node argumentNode = new DeclareCommandsPacket.Node();

            argumentNode.flags = DeclareCommandsPacket.getFlag(DeclareCommandsPacket.NodeType.LITERAL,
                    executable, false, false);
            argumentNode.name = groups.get(i).name();
            nodes[i] = argumentNode;
        }
        nodeMaker.addNodes(nodes);
    }
}
