package de.hsmainz.cs.semgis.wfs.resultformatter.vector;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.jena.query.ResultSet;

import de.hsmainz.cs.semgis.wfs.resultformatter.VectorResultFormatter;
import de.hsmainz.cs.semgis.wfs.resultstyleformatter.StyleObject;

public class ESRIJSONFormatter extends VectorResultFormatter {

	@Override
	public String formatter(ResultSet results, String startingElement, String featuretype, String propertytype,
			String typeColumn, Boolean onlyproperty, Boolean onlyhits, String srsName, String indvar, String epsg,
			List<String> eligiblenamespaces, List<String> noteligiblenamespaces, StyleObject mapstyle,
			Boolean alternativeFormat, Boolean invertXY, Boolean coverage, Writer out)
			throws XMLStreamException, IOException {
		/*JsonFactory jfactory = new JsonFactory();
		JsonGenerator jGenerator = jfactory.createGenerator(out);
		jGenerator.writeStartObject();
		jGenerator.writeNumberField("amount", this.lastQueriedElemCount);
		jGenerator.writeStartArray();*/
		while(results.hasNext()) {
			results.next();
		}
		// TODO Auto-generated method stub
		return null;
	}

}
