package pl.frazeusz.writer;

import java.io.File;
import java.util.Map;

public class WriterAPI {
	private JSONParser jsonParser = null;
	
	
	public WriterAPI(){
		jsonParser = new JSONParser();
	}


	void save(File file, Map<Phrase, Integer> resultsToSave) {
		jsonParser.toJSON(file, resultsToSave);
	}


	Map<Phrase, Integer> read(File file) {
		return jsonParser.readFromJSON(file);
	}
	
	
}
