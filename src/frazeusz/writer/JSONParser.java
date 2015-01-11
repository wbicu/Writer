package frazeusz.writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {
	
	public JSONParser() {}

	public void toJSON(File file, Map<Phrase, Integer> resultsToSave) {
		JSONArray calosc = new JSONArray();
		for (Map.Entry<Phrase, Integer> r : resultsToSave.entrySet()){
			Phrase key = r.getKey();
			Integer count = r.getValue();
			String phrase = key.getPhrase();
			boolean synonyms = key.getWordVariant().isSynonyms();
			boolean variants = key.getWordVariant().isVariants();
			boolean diminutives = key.getWordVariant().isDiminutives();
			
			JSONObject entry = new JSONObject();
			JSONObject wordVariant = new JSONObject();
			wordVariant.put("synonyms", synonyms);
			wordVariant.put("variants", variants);
			wordVariant.put("diminutives", diminutives);
			
			entry.put("phrase", phrase);
			entry.put("wordVariant", wordVariant);
			entry.put("count", count);
			
			calosc.put(entry);
		}
		
		System.out.println(calosc.toString());
		try {
			Files.write(file.toPath(), calosc.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public Map<Phrase, Integer> readFromJSON(File file) {
		Map<Phrase, Integer> mapa = new HashMap<Phrase, Integer>();
		JSONArray array;
		String content;
		try {
			content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			array = new JSONArray(content);
			//System.out.println(array.toString());
			//System.out.println(array.length());
			
			for (int i=0; i<array.length(); i++){
				JSONObject tempEntry = array.getJSONObject(i);
				JSONObject tempFlags = tempEntry.getJSONObject("wordVariant");
				
				Integer count = tempEntry.getInt("count");
				String phrase = tempEntry.getString("phrase");
				boolean synonyms = tempFlags.getBoolean("synonyms");
				boolean variants = tempFlags.getBoolean("variants");
				boolean diminutives = tempFlags.getBoolean("diminutives");
				
				WordVariant tempWordVariant = new WordVariant(synonyms, variants, diminutives);
				
				Phrase tempPhrase = new Phrase(phrase, tempWordVariant);
				mapa.put(tempPhrase, count);
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return mapa;
	}
	
	
}
