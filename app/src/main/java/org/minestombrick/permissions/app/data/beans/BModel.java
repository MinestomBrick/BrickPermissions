package org.minestombrick.permissions.app.data.beans;

import org.minestombrick.ebean.BaseModel;
import org.minestombrick.permissions.app.data.BrickPermissionsDatabaseContext;
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