package com.bucaresystems.fasterysync.process;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import org.apache.commons.io.comparator.NameFileComparator;
import com.bucaresystems.fasterysync.base.CustomProcess;
import com.bucaresystems.fasterysync.util.SqlBuilder;

public class BSCA_CreateFasteryPOSTable extends CustomProcess{

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		File f = new File(BSCA_CreateFasteryPOSTable.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String parentPath = f.getPath();
		File sqlListFile = new File(parentPath+"/sql/");
		String[] listSql = sqlListFile.list();
		 Arrays.sort(listSql);
		for (String fileName : listSql) {
			String sql = SqlBuilder.builder().file("sql/"+fileName).build();
			System.out.println(sql);
		}
			
		return null;
	}

}
