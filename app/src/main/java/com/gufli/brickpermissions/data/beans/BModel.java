package com.gufli.brickpermissions.data.beans;

import com.gufli.brickpermissions.data.BrickPermissionsDatabaseContext;
import com.gufli.brickutils.database.BaseModel;
import io.ebean.Model;
import io.ebean.annotation.DbName;

import javax.persistence.MappedSuperclass;

@DbName(BrickPermissionsDatabaseContext.DATASOURCE_NAME)
@MappedSuperclass
public class BModel extends Model implements BaseModel {

    public BModel() {
        super(BrickPermissionsDatabaseContext.DATASOURCE_NAME);
    }

}