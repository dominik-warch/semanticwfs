package de.hsmainz.cs.semgis.wfs.resultformatter;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;

import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.HDTFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.HexTuplesFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.N3Formatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.NQuadsFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.NTFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.RDFFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.RDFJSONFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.RDFThriftFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.TTLFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.TrigFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.rdf.TrixFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.CSVFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.GMLFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.GPXFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.GeoHashFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.GeoJSONFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.GeoJSONLDFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.GeoJSONSeqFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.GeoURIFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.JSONFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.JSONLDFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.JSONPFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.JSONSeqFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.KMLFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.LatLonTextFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.MVTFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.MapMLFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.OSMFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.OSMLinkFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.SVGFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.WKBFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.WKTFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.XLSFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.XLSXFormatter;
import de.hsmainz.cs.semgis.wfs.resultformatter.vector.YAMLFormatter;
import de.hsmainz.cs.semgis.wfs.util.ReprojectionUtils;

public abstract class VectorResultFormatter extends ResultFormatter{
	
	String featureType="";
	
	public static final String WKTLiteral="http://www.opengis.net/ont/geosparql#wktLiteral";
	
	public Geometry parseVectorLiteral(String literalValue, String literalType, String epsg, String srsName) {
		Geometry geom=null;
		if(literalType.toLowerCase().contains("wkt")) {
			try {
				geom=this.wktreader.read(literalValue);
			} catch (ParseException e) {
				return null;
			}
		}
		else if(literalType.toLowerCase().contains("geojson")) {
			geom=this.geojsonreader.read(literalValue);
		}
		else if(literalType.toLowerCase().contains("wkb")) {
			try {
				geom=this.wkbreader.read(WKBReader.hexToBytes(literalValue));
			} catch (ParseException e) {
				return null;
			}
		}
		if(geom!=null) {
			geom=ReprojectionUtils.reproject(geom, epsg,srsName);
			return geom;
		}
		return null;
	}
	
	static {
		ResultFormatter format=new GeoJSONFormatter();
		resultMap.put("geojson", format);
		resultMap.put("json", format);
		labelMap.put("geojson",format.label);
		resultMap.put(format.mimeType, format);
		format=new GeoJSONSeqFormatter();
		resultMap.put("geojsonseq", format);
		labelMap.put("geojsonseq",format.label);
		resultMap.put(format.mimeType, format);
		format=new GeoJSONLDFormatter();
		resultMap.put("geojsonld", format);
		labelMap.put("geojsonld",format.label);
		resultMap.put(format.mimeType, format);
		format=new GeoHashFormatter();
		resultMap.put("geohash", format);
		labelMap.put("geohash",format.label);
		resultMap.put(format.mimeType, format);
		format=new GeoURIFormatter();
		resultMap.put("geouri", format);
		labelMap.put("geouri",format.label);
		resultMap.put(format.mimeType, format);
		format=new TrigFormatter();
		resultMap.put("trig", format);
		labelMap.put("trig",format.label);
		resultMap.put(format.mimeType, format);
		format=new TrixFormatter();
		resultMap.put("trix", format);
		labelMap.put("trix",format.label);
		resultMap.put(format.mimeType, format);
		format=new KMLFormatter();
		resultMap.put("kml", format);
		labelMap.put("kml",format.label);
		resultMap.put(format.mimeType, format);
		format=new GMLFormatter();
		resultMap.put("gml", format);
		labelMap.put("gml",format.label);
		resultMap.put(format.mimeType, format);
		format=new N3Formatter();
		resultMap.put("n3", format);
		labelMap.put("n3",format.label);
		resultMap.put(format.mimeType, format);
		format=new NQuadsFormatter();
		resultMap.put("nq", format);
		labelMap.put("nq",format.label);
		resultMap.put(format.mimeType, format);
		format=new NTFormatter();
		resultMap.put("nt", format);
		labelMap.put("nt",format.label);
		resultMap.put(format.mimeType, format);
		format=new RDFThriftFormatter();
		resultMap.put("rt", format);
		labelMap.put("rt",format.label);
		resultMap.put(format.mimeType, format);
		format=new NTFormatter();
		resultMap.put("nt", format);
		labelMap.put("nt",format.label);
		resultMap.put(format.mimeType, format);
		format=new MapMLFormatter();
		resultMap.put("mapml", format);
		labelMap.put("mapml",format.label);
		resultMap.put(format.mimeType, format);
		format=new OSMFormatter();
		resultMap.put("osm", format);
		labelMap.put("osm",format.label);
		resultMap.put(format.mimeType, format);
		format=new WKTFormatter();
		resultMap.put("wkt", format);
		labelMap.put("wkt",format.label);
		resultMap.put(format.mimeType, format);
		format=new WKBFormatter();
		resultMap.put("wkb", format);
		labelMap.put("wkb",format.label);
		resultMap.put(format.mimeType, format);
		format=new GPXFormatter();
		resultMap.put("gpx", format);
		labelMap.put("gpx",format.label);
		resultMap.put(format.mimeType, format);
		format=new RDFFormatter();
		resultMap.put("rdf", format);
		labelMap.put("rdf",format.label);
		resultMap.put(format.mimeType, format);
		format=new HDTFormatter();
		resultMap.put("hdt", format);
		labelMap.put("hdt",format.label);
		resultMap.put(format.mimeType, format);
		format=new RDFJSONFormatter();
		resultMap.put("rdfjson", format);
		labelMap.put("rdfjson",format.label);
		resultMap.put(format.mimeType, format);
		format=new TTLFormatter();
		resultMap.put("ttl", format);
		labelMap.put("ttl",format.label);
		resultMap.put(format.mimeType, format);
		format=new SVGFormatter();
		resultMap.put("svg", format);
		labelMap.put("svg",format.label);
		resultMap.put(format.mimeType, format);
		format=new CSVFormatter();
		resultMap.put("csv", format);
		labelMap.put("csv",format.label);
		resultMap.put(format.mimeType, format);
		format=new JSONSeqFormatter();
		resultMap.put("jsonseq", format);
		labelMap.put("jsonseq",format.label);
		resultMap.put(format.mimeType, format);
		format=new JSONLDFormatter();
		resultMap.put("jsonld", format);
		labelMap.put("jsonld",format.label);
		resultMap.put(format.mimeType, format);
		format=new JSONPFormatter();
		resultMap.put("jsonp", format);
		labelMap.put("jsonp",format.label);
		resultMap.put(format.mimeType, format);
		format=new JSONFormatter();
		resultMap.put("jsonn", format);
		labelMap.put("jsonn",format.label);
		resultMap.put(format.mimeType, format);
		format=new HexTuplesFormatter();
		resultMap.put("hextuples", format);
		labelMap.put("hextuples",format.label);
		resultMap.put(format.mimeType, format);
		format=new LatLonTextFormatter();
		resultMap.put("latlon", format);
		labelMap.put("latlon",format.label);
		resultMap.put(format.mimeType, format);
		format=new MVTFormatter();
		resultMap.put("mvt", format);
		labelMap.put("mvt",format.label);
		resultMap.put(format.mimeType, format);
		format=new OSMLinkFormatter();
		resultMap.put("osmlink", format);
		labelMap.put("osmlink",format.label);
		resultMap.put(format.mimeType, format);
		format=new HTMLFormatter();
		resultMap.put("html", format);
		labelMap.put("html",format.label);
		resultMap.put(format.mimeType, format);
		format=new GeoURIFormatter();
		resultMap.put("geouri", format);
		labelMap.put("geouri",format.label);
		resultMap.put(format.mimeType, format);
		format=new XLSFormatter();
		resultMap.put("xls", format);
		labelMap.put("xls",format.label);
		resultMap.put(format.mimeType, format);
		format=new XLSXFormatter();
		resultMap.put("xlsx", format);
		labelMap.put("xlsx",format.label);
		resultMap.put(format.mimeType, format);
		format=new YAMLFormatter();
		resultMap.put("yaml", format);
		labelMap.put("yaml",format.label);
		resultMap.put(format.mimeType, format);
	}

}
