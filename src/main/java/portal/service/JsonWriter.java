package portal.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


import portal.models.ReportModel;

public interface JsonWriter {
	public ByteArrayOutputStream writeJsonWithNoNull(ReportModel reportModel) throws Exception;
	public ByteArrayInputStream writeJsonWithNoNullIn(ReportModel reportModel) throws Exception;
}
