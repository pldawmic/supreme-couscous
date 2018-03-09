package jersey.example1.pojo;

public class Adage {

	private String words;
	private int wordCount;
	
	public Adage() {}
	
	public void setWords(String words) {
		this.words = words;
		this.wordCount = words.trim().split("\\s+").length;
	}
	
	public String getWords() {return this.words;}
	
	public void setWordCount(int wordCount) {this.wordCount = wordCount;}
	public int getWordCount() {return this.wordCount;}
	
	@Override
	public String toString() {
		return this.words;
	}
}
