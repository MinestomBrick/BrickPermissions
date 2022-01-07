package com.gufli.brickpermissions.data.beans;

import com.gufli.brickpermissions.data.converters.NBTConverter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BPermission extends BModel {

    private final String permission;

    @Convert(converter = NBTConverter.class, attributeName = "data")
    private NBTCompound data;

    public BPermission(@NotNull String permission, @Nullable NBTCompound data) {
        super();
        this.permission = permission;
        this.data = data;
    }

    // getters

    public String permission() {
        return permission;
    }

    public NBTCompound data() {
        return data;
    }

    // setters

    public void setData(NBTCompound data) {
        this.data = data;
    }

}
