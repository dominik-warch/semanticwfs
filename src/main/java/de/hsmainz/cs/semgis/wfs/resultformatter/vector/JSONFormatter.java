package de.hsmainz.cs.semgis.wfs.resultformatter.vector;

import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.json.JSONArray;
import org.json.JSONObject;

import de.hsmainz.cs.semgis.wfs.resultformatter.WFSResultFormatter;
import de.hsmainz.cs.semgis.wfs.resultstyleformatter.StyleObject;

/**
 * Formats a query result to JSON.
 */
public class JSONFormatter extends WFSResultFormatter {

	/**
	 * Constructor for this class.
	 */
	public JSONFormatter() {
		this.mimeType="application/json";
		this.exposedType="application/json";
		this.urlformat="jsonn";
		this.label="JSON";
		this.fileextension="json";
	}
	
	@Override
	public String formatter(ResultSet results,String startingElement,
			String featuretype,String propertytype,
			String typeColumn,Boolean onlyproperty,Boolean onlyhits,
			String srsName,String indvar,String epsg,List<String> eligiblenamespaces,
			List<String> noteligiblenamespaces,StyleObject mapstyle,Boolean alternativeFormat,Boolean invertXY) throws XMLStreamException {
		JSONObject result=new JSONObject();
	    JSONArray obj=new JSONArray();
	    result.put("features",obj);
	    while(results.hasNext()) {
	    	this.lastQueriedElemCount++;
	    	QuerySolution solu=results.next();
	    	JSONObject jsonobj=new JSONObject();
	    	Iterator<String> varnames = solu.varNames();
	    	while(varnames.hasNext()) {
	    		String name=varnames.next();
    			try {
    				Literal lit=solu.getLiteral(name);
    				jsonobj.put(name,lit.getString());
    			}catch(Exception e) {
    				jsonobj.put(name,solu.get(name));	
    			}  		
	    	}
    		obj.put(jsonobj);
	    }	    	
	    result.put("amount",this.lastQueriedElemCount);
	    return result.toString(2);
	}

}