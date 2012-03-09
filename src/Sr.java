import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
public class Sr {
	public static final String GENERATION="Generation";
	public static final String BIGENERATION="Best Individual of Generation:";
	public static final String MEANHIGESTDIFF="Mean Highest Diff=";
	public static final String MEANSOLVEDSCORE="Mean Solved Score=";
	public static final String MEANENTROPY="Mean Entropy Score=";
	public static final String MEANSTEPS="Steps Mean=";
	public static final String HIGESTDIFF="Highest Diff=";
	public static final String SOLVEDSCORE="Solved Score=";
	public static final String ENTROPY="Entropy Score=";
	public static final String STEPS="Steps Mean=";
	public static final String TIME="Time:";

	@SuppressWarnings("deprecation")
	public static Vector<Generation>readRun(File file) throws IOException, Exception{
		BufferedReader ibr=new BufferedReader(new FileReader(file));
		String linea="";
		String nuevalinea="";
		boolean readbest=false;
		boolean readmean=false;
		Vector<Generation>run=new Vector<Generation>();
		Generation gen=new Generation();
		Generation gen0=null;
		Generation genAnt=null;
		while((linea=ibr.readLine())!=null){
			if(linea.indexOf(BIGENERATION)>=0){
				ibr.readLine();//hay dos lineas
				linea=ibr.readLine();
				
				String sub=linea.substring(linea.indexOf(HIGESTDIFF)+HIGESTDIFF.length());
				StringTokenizer st=new StringTokenizer(sub);
				gen.highestdiff=Float.parseFloat(st.nextToken());
				nuevalinea+=gen.highestdiff+";";
				
				
				
				sub=linea.substring(linea.indexOf(SOLVEDSCORE)+SOLVEDSCORE.length());
				st=new StringTokenizer(sub);
				gen.solvedscore=Float.parseFloat(st.nextToken());
				nuevalinea+=gen.solvedscore+";";

				
				sub=linea.substring(linea.indexOf(ENTROPY)+ENTROPY.length());
				st=new StringTokenizer(sub);
				StringTokenizer st2=new StringTokenizer(st.nextToken(),"/");
				gen.entropy=Float.parseFloat(st2.nextToken());
				nuevalinea+=gen.entropy+";";

				
				sub=linea.substring(linea.indexOf(STEPS)+STEPS.length());
				st=new StringTokenizer(sub);
				gen.steps=Float.parseFloat(st.nextToken());
				nuevalinea+=gen.steps+";";

				readbest=true;
			}else if(linea.indexOf(GENERATION)>=0){
				String sub=linea.substring(linea.indexOf(GENERATION)+GENERATION.length());
				StringTokenizer st=new StringTokenizer(sub);
				gen.number=Float.parseFloat(st.nextToken());
				nuevalinea+=gen.number+";";

				sub=linea.substring(linea.indexOf(TIME)+TIME.length());
				gen.fecha=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy").parse(sub.trim());
				nuevalinea+=gen.number+";";
				
				if(genAnt!=null&&gen0!=null){

					gen.tiempoDif=gen.fecha.getTime()-genAnt.fecha.getTime();
					gen.tiempoTotal=gen.fecha.getTime()-gen0.fecha.getTime();
					gen.tiempoDif2=gen.tiempoDif-genAnt.tiempoDif;
				}else{
					gen.tiempoDif2=0;
					gen.tiempoDif=0;
					gen.tiempoTotal=0;
				}
				
				ibr.readLine();//hay 5 lineas
				ibr.readLine();//hay 5 lineas
				ibr.readLine();//hay 5 lineas
				ibr.readLine();//hay 5 lineas
				linea=ibr.readLine();
				
				sub=linea.substring(linea.indexOf(MEANHIGESTDIFF)+MEANHIGESTDIFF.length());
				st=new StringTokenizer(sub);
				gen.meanhighestdiff=Float.parseFloat(st.nextToken());
				nuevalinea+=gen.meanhighestdiff+";";
				
				
				
				sub=linea.substring(linea.indexOf(MEANSOLVEDSCORE)+MEANSOLVEDSCORE.length());
				st=new StringTokenizer(sub);
				gen.meansolvedscore=Float.parseFloat(st.nextToken());
				nuevalinea+=gen.meansolvedscore+";";

				
				sub=linea.substring(linea.indexOf(MEANENTROPY)+MEANENTROPY.length());
				st=new StringTokenizer(sub);
				gen.meanentropy=Float.parseFloat(st.nextToken());
				nuevalinea+=gen.meanentropy+";";

				
				sub=linea.substring(linea.indexOf(MEANSTEPS)+MEANSTEPS.length());
				st=new StringTokenizer(sub);
				gen.meansteps=Float.parseFloat(st.nextToken());
				nuevalinea+=gen.meansteps+";";

				readmean=true;
			}
			
			if(readbest&&readmean){
				run.add(gen);
				System.out.println(nuevalinea);
				if(gen.number==0){
					gen0=gen;
				}
				genAnt=gen;
				nuevalinea="";
				gen=new Generation();
				readbest=false;
				readmean=false;
			}
		}
		ibr.close();
		return run;
		
	}
	
	public static void main(String[]args) throws Exception{
		File file=new File("pruebas");
		File[]pruebas=file.listFiles();
		String salida="";
		Vector<Vector<Vector<Generation>>>pruebs=new Vector<Vector<Vector<Generation>>>();
		for(int i=0;i<pruebas.length;i++){
			String nombrep=pruebas[i].getName();
			if(pruebas[i].isDirectory()){
			File[]runs=pruebas[i].listFiles();
			Vector<Vector<Generation>>prueba=new Vector<Vector<Generation>>();
			for(int j=0;j<runs.length;j++){
				File fichero =new File(runs[j].getAbsolutePath()+"/"+nombrep+".stat");
				if(fichero.exists()){
					System.out.println("Reading "+fichero.getAbsolutePath()+"...");
					prueba.add(Sr.readRun(fichero));
					System.out.println("Ok.");
				}
			}
			pruebs.add(prueba);
			}
		}
		
		Vector<Vector<Generation>>mediapruebas=new Vector<Vector<Generation>>();
		Iterator<Vector<Vector<Generation>>>ps=pruebs.iterator();
		while(ps.hasNext()){
			Iterator<Vector<Generation>>p=ps.next().iterator();
			Vector<Generation>mediapruebaX=new Vector<Generation>();
			int num=0;
			while(p.hasNext()){
				Iterator<Generation>r=p.next().iterator();
				int i=0;
				salida+="Run "+num+"\n";
				salida+=Generation.getTitulo(num)+"\n";
				while(r.hasNext()){
					Generation mean;
					try{
						mean=mediapruebaX.get(i);
					}catch(ArrayIndexOutOfBoundsException e){
						mean=new Generation();
						mediapruebaX.add(mean);
					}
					Generation gen=r.next();
					mean.entropy+=gen.entropy;
					mean.highestdiff+=gen.highestdiff;
					mean.meanentropy+=gen.meanentropy;
					mean.meanhighestdiff+=gen.meanhighestdiff;
					mean.meansolvedscore+=gen.meansolvedscore;
					mean.meansteps+=gen.meansteps;
					mean.number+=gen.number;
					mean.solvedscore+=gen.solvedscore;
					mean.steps+=gen.steps;
					mean.tiempoDif+=gen.tiempoDif;
					mean.tiempoDif2+=gen.tiempoDif2;
					mean.tiempoTotal+=gen.tiempoTotal;
					salida+=gen+"\n";
					i++;
				}
				num++;
			}
			for(int i=0;i<mediapruebaX.size();i++){
				Generation mean=mediapruebaX.get(i);
				mean.entropy=mean.entropy/num;
			mean.highestdiff=mean.highestdiff/num;
			mean.meanentropy=mean.meanentropy/num;
			mean.meanhighestdiff=mean.meanhighestdiff/num;
			mean.meansolvedscore=mean.meansolvedscore/num;
			mean.meansteps=mean.meansteps/num;
			mean.number=mean.number/num;
			mean.solvedscore=mean.solvedscore/num;
			mean.steps=mean.steps/num;
			mean.tiempoDif2=mean.tiempoDif2/num;
			mean.tiempoDif=mean.tiempoDif/num;
			mean.tiempoTotal=mean.tiempoTotal/num;
			}
			
			
			mediapruebas.add(mediapruebaX);
		}
		
		salida+="\nMEDIAS\n";
		Iterator<Vector<Generation>>imp=mediapruebas.iterator();
		Generation[][]array=new Generation[mediapruebas.size()][];
		int i=0;
		while(imp.hasNext()){
			array[i]=imp.next().toArray(new Generation[0]);
			i++;
		}
		for(i=0;i<array.length;i++){
			salida+=Generation.getTitulo(i);
		}
		salida+="\n";
		for(int j=0;j<array[0].length;j++){
			for(i=0;i<array.length;i++){
				salida+=array[i][j];
			}
			salida+="\n";
		}
		System.out.println(salida);
		
		FileWriter fw=new FileWriter("excel.csv");
		fw.write(salida);
		fw.close();
	}

}
