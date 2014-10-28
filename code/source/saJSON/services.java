package saJSON;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2014-10-28 20:45:23 GMT
// -----( ON-HOST: 192.168.79.128

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
// --- <<IS-END-IMPORTS>> ---

public final class services

{
	// ---( internal utility methods )---

	final static services _instance = new services();

	static services _newInstance() { return new services(); }

	static services _cast(Object o) { return (services)o; }

	// ---( server methods )---




	public static final void jsonToXML (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(jsonToXML)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required jsonString
		// [o] field:0:required xmlString
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	jsonString = IDataUtil.getString( pipelineCursor, "jsonString" );
		
		String xmlString = "";
		
		if(jsonString != null) {
			try {
				JSONObject obj = new JSONObject(jsonString);
				xmlString = XML.toString(obj);
			} catch(Exception ex) {}
		}
		
		IDataUtil.put( pipelineCursor, "xmlString", xmlString );
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void toJSON (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(toJSON)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] record:0:required document
		// [o] field:0:required jsonString
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		IData	document = IDataUtil.getIData( pipelineCursor, "document" );
		
		String jsonString = "";
		
		try {
			jsonString = toJSONAux(document).toString();
		} catch(Exception ex) {}
		
		IDataUtil.put( pipelineCursor, "jsonString", jsonString );
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static JSONObject toJSONAux(IData document) throws Exception
	{
	    JSONObject obj=new JSONObject();
		if ( document != null)
		{
			String key = "";
			Object value = null;
			
			IDataCursor cursor = document.getCursor();
			
			while(cursor.next()) {
	
				key = cursor.getKey();
				value = cursor.getValue();
	
				if(value instanceof IData) {
					obj.put(key,toJSONAux((IData)value));
				}
				else if(value instanceof IData[]) {
					JSONArray list = new JSONArray();
					for(int i=0;i<((IData[])value).length;i++)
						list.put(toJSONAux(((IData[])value)[i]));
					obj.put(key,list);
				}
				else if(value instanceof String[]) {
					JSONArray list = new JSONArray();
					for(int i=0;i<((String[])value).length;i++)
						list.put(((String[])value)[i]);
					obj.put(key,list);
				}
				else {
					obj.put(key,value);
				}        
			}
		}
	    return obj;
	}
	// --- <<IS-END-SHARED>> ---
}

