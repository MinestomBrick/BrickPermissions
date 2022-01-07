package com.gufli.brickpermissions.data.beans;

import com.gufli.brickpermissions.data.DatabaseContext;
import io.ebean.Model;
import io.ebean.annotation.DbName;

import javax.persistence.MappedSuperclass;

@DbName(DatabaseContext.DATASOURCE_NAME)
@MappedSuperclass
public class BModel extends Model {

    public BModel() {
        super(DatabaseContext.DATASOURCE_NAME);
    }

}