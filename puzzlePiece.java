import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class puzzlePiece{
	
	BufferedImage img = null;
	int[][][] pieces;
	
	public puzzlePiece(String path){
		
		this.setimage(path); 
		
		
		
	}
	
	
	private void setimage(String path){
		
		File file = new File(path);
		
		try {
			img = ImageIO.read(file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.imageToArray();
	}
	
	
	//Puts the loaded image into a 3 dimensional array, Piece(20)->Width(32)->Height(359)
	private void imageToArray(){
		
		
		int[][] imageArray = new int[img.getWidth()][img.getHeight()];

		//put the buffered image into image array and set to absolute values for processing
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++){
				imageArray[i][j] = Math.abs(img.getRGB(i, j));
			}
		
		
		pieces = new int[20][32][359];
		
		//copies all pieces into the place.  20x32x359
		for (int pieceNumber = 0; pieceNumber < 20; pieceNumber++) {
			for (int piecewidth = 0; piecewidth < 32; piecewidth++) {
				for (int height = 0; height < 359; height++) {
					pieces[pieceNumber][piecewidth][height] = imageArray[(pieceNumber*32) + piecewidth][height];
				}
			}
		}
	}
	
	
	public double matchPercentwithZoneExclusion(int numberOfZones, int[] columnA, int[] columnB){
		double matched = 0;
		double lowest = 100;
		double total = 0;
		
		int sectionAmount = columnA.length / numberOfZones;
		double[] storeAnswers = new double[numberOfZones];
		
		//store all zone percentage matches
		for (int zone = 0; zone < numberOfZones; zone++){
			if (zone != numberOfZones -1)
				storeAnswers[zone] = compareEdge(breakArray(columnA, zone*sectionAmount, ((zone+1)*sectionAmount)-1), breakArray(columnB, zone*sectionAmount, ((zone+1)*sectionAmount)-1)); 
			
			else storeAnswers[zone] = compareEdge(breakArray(columnA, zone*sectionAmount, ((zone+1)*sectionAmount)-1 + columnA.length%numberOfZones), breakArray(columnB, zone*sectionAmount, ((zone+1)*sectionAmount)-1 + columnA.length%numberOfZones)); 
		}
		
		//find lowest and store it
		for (int i = 0; i < storeAnswers.length; i++)
			if (storeAnswers[i] < lowest)
				lowest = storeAnswers[i];
		//add up percentages except lowest
		for (int i = 0; i < storeAnswers.length; i++)
			if (storeAnswers[i] != lowest)
				total += storeAnswers[i];
		
		return total;
	}
	private int[] breakArray(int toBeBroken[], int lower, int upper){
		int[] broken = new int[upper - lower];
	
		for (int i = 0; i < upper-lower; i++){
			broken[i] = toBeBroken[lower +i];
		}
		return broken;
	}
	
	public double compareEdge(int[] leftEdge, int[] rightEdge){
		double percent = 0;
		long sum1 = 0;
		long sum2 = 0;
		//This is the percentage matching way.
		for (int i = 0; i < leftEdge.length; i ++){
			
				if (leftEdge[i] < rightEdge[i]) percent += leftEdge[i] *100 / rightEdge[i];
				
				else 
					percent += rightEdge[i] *100 / leftEdge[i];
			
		}
		return percent/(leftEdge.length);
	
		
		
		
		//This is the Yume Sum way:  EDIT: this does not seem to work, long/long of that size returns 0  
		/*for (int i = 0; i < leftEdge.length; i++){
			sum1 += leftEdge[i];
			sum2 += rightEdge[i];
		}
		if (sum1 > sum2) percent = sum1 - sum2;
		else percent = sum2 - sum1;
		System.out.println(percent);
		return percent;
		*/
		
	}
	
	//Grabs the entire column and puts it into a one dimentional array for easy processing.
	private int[] getColumn(int pieceNumber, int columnNumber){
		
		
		int[] rowArray = new int[pieces[0][0].length];
		
		for (int i = 0; i < rowArray.length; i++){
			rowArray[i] = pieces[pieceNumber][columnNumber][i];
		}
		return rowArray;
	}



/*
public void arrangePeices(){
		
		//find Leftmost Piece.
		//this.switchPiece(8, 0);
		
		
		int bestFitPiece = 0;
		double maxpercent = 0.0;
		double percentMatch = 0.0;
		
		for (int basePiece = 18; basePiece > 0; basePiece--) {

			// Locate Best Fit Piece
			for (int pieceNumber = basePiece; pieceNumber > 0; pieceNumber--) {
				//switch compareEdge with matchPercentwithZoneExclusion
				//percentMatch = this.compareEdge(this.getColumn(basePiece - 1, 31), this.getColumn(pieceNumber, 0));
				percentMatch = this.matchPercentwithZoneExclusion(4, this.getColumn(basePiece + 1, 0), this.getColumn(pieceNumber, 31));
				if (percentMatch > maxpercent){
					maxpercent = percentMatch;
					bestFitPiece = pieceNumber;
				}
			}
			System.out.println("Best Fit found for " + basePiece + " with PieceNumber " + bestFitPiece);

			// Switch Piece into that place
			this.switchPiece(basePiece, bestFitPiece);
			maxpercent = 0.0;

		}
		this.outputFile();
		
}	
*/
	
private int leftmost(){
	int leftmostPiece = 0;

	for (int piece = 0; piece < 21; piece++){
		
	}
	
	return leftmostPiece;
}
	public void arrangePeices(){
		
		//find Leftmost Piece.
		
		this.switchPiece(8, 0);
		
		
		int bestFitPiece = 0;
		double maxpercent = 0.0;
		double percentMatch = 0.0;
		
		for (int basePiece = 1; basePiece < 20; basePiece++) {

			// Locate Best Fit Piece
			for (int pieceNumber = basePiece; pieceNumber < 20; pieceNumber++) {
				//switch compareEdge with matchPercentwithZoneExclusion
				//percentMatch = this.compareEdge(this.getColumn(basePiece - 1, 31), this.getColumn(pieceNumber, 0));
				percentMatch = this.matchPercentwithZoneExclusion(3, this.getColumn(basePiece - 1, 31), this.getColumn(pieceNumber, 0));
				if (percentMatch > maxpercent){
					maxpercent = percentMatch;
					bestFitPiece = pieceNumber;
				}
			}
			System.out.println("Best Fit found for " + basePiece + " with PieceNumber " + bestFitPiece);

			// Switch Piece into that place
			this.switchPiece(basePiece, bestFitPiece);
			maxpercent = 0.0;

		}
		this.outputFile();
		
}

	private void switchPiece(int basePiece, int bestFitPiece){
	
		int[][] tempArray = new int[32][359];
		
		//Load tempArray with first piece
		for(int width = 0; width < 32; width++){
			for (int height = 0; height < 359; height++){
				tempArray[width][height] = pieces[basePiece][width][height]; //first to temp
				pieces[basePiece][width][height] = pieces[bestFitPiece][width][height]; //second to first
				pieces[bestFitPiece][width][height] = tempArray[width][height]; //temp to second
			}
		}
		
	}
	
	private void outputFile(){
		
		int[][] finalArray = new int[img.getWidth()][img.getHeight()];  //create entire array.
		
		for (int pieceNum = 0; pieceNum < 20; pieceNum++){
			for (int width = 0; width < 32; width++){
				for (int height = 0; height < 359; height++){
					finalArray[((pieceNum*32) + width)][height] = pieces[pieceNum][width][height];
				}
			}
		}
		
		for (int width = 0; width < finalArray.length; width++){
			
			for(int height = 0; height < finalArray[0].length; height++){
				img.setRGB(width, height, finalArray[width][height]*-1);
			}
		}
		
		try {
			
            ImageIO.write(img, "png",new File("Tokyo Panorama Assembled.png"));
			//img.write("C:\\Users\\Arthur\\Dropbox\\Eclipse Projects\\imageShear\\Ouput", "output.png");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	

	public static void main(String[] args){
		puzzlePiece puzzle = new puzzlePiece("Tokyo Panorama Shredded.png");
		int a = 60;
		System.out.println(a/7);
		System.out.println(a%7);
		puzzle.arrangePeices();
		
	}















}
