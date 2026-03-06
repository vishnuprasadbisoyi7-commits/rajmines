package tech.csm.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class VendorFileDaoImpl implements VendorFileDao {

	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	
	 @Override
	    public void saveFile(String vendorId, String fileName) {

	        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
	                .withSchemaName("rajmines_vts")
	                .withProcedureName("save_vendor_file");

	        call.execute(Map.of(
	                "p_vendor_id", vendorId,
	                "p_file_name", fileName
	        ));
	    }

}
