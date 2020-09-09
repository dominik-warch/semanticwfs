package de.hsmainz.cs.semgis.wfs.resultformatter.vector;

import java.util.List;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;

import org.apache.jena.query.ResultSet;
import org.json.JSONObject;
import org.wololo.jts2geojson.GeoJSONReader;

import de.hsmainz.cs.semgis.wfs.resultformatter.ResultFormatter;
import de.hsmainz.cs.semgis.wfs.resultstyleformatter.StyleObject;
import no.ecc.vectortile.VectorTileEncoder;

public class MVTFormatter extends ResultFormatter {

	public MVTFormatter() {
		this.urlformat="mvt";
		this.label="Mapbox Vector Tiles (MVT)";
		this.mimeType="text/mvt";
		this.exposedType="text/mvt";
	}
	
	@Override
	public String formatter(ResultSet results, String startingElement, String featuretype, String propertytype,
			String typeColumn, Boolean onlyproperty, Boolean onlyhits, String srsName, String indvar, String epsg,
			List<String> eligiblenamespaces, List<String> noteligiblenamespaces,StyleObject mapstyle,Boolean alternativeFormat,Boolean invertXY) throws XMLStreamException {
		ResultFormatter format = resultMap.get("geojson");
		JSONObject geojson = new JSONObject(
				format.formatter(results,startingElement, featuretype,propertytype, typeColumn, onlyproperty,onlyhits,"",indvar,epsg,eligiblenamespaces,noteligiblenamespaces,mapstyle,alternativeFormat,invertXY));
		this.lastQueriedElemCount = format.lastQueriedElemCount;
		VectorTileEncoder encoder = new VectorTileEncoder();
		GeoJSONReader reader=new GeoJSONReader();	
		for(int i=0;i<geojson.getJSONArray("features").length();i++) {
			JSONObject feature=geojson.getJSONArray("features").getJSONObject(i);
			if(feature.has("properties")) {
				encoder.addFeature(featuretype, new TreeMap<>(),reader.read(feature.getJSONObject("geometry").toString()));				
			}else {
				encoder.addFeature(featuretype, geojson.getJSONObject("properties").toMap(),reader.read(feature.getJSONObject("geometry").toString()));				
			}
		}
		byte[] encoded = encoder.encode();
		return encoded.toString();
	}

}
