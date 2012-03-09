import java.util.Date;


public class Generation {
	public float number;
	public float meanhighestdiff;
	public float meansolvedscore;
	public float meanentropy;
	public float meansteps;
	public float highestdiff;
	public float solvedscore;
	public float entropy;
	public float steps;
	public long tiempoDif2;
	public long tiempoDif;
	public long tiempoTotal;
	public Date fecha;
	public String toString(){
		return number+";"+meanhighestdiff+";"+meansolvedscore+";"+meanentropy+";"+meansteps+";"+highestdiff+";"+solvedscore+";"+entropy+";"+steps+";"+fecha+";"+tiempoDif+";"+tiempoDif2+";"+tiempoTotal+";";
	}
	public static String getTitulo(int prueba){
		return "Generacion "+prueba+";"+"Mean Highest Diff "+prueba+";"+"Mean Solved Score "+prueba+";"+"Mean Entropy "+prueba+";"+"Mean Steps "+prueba+";"+"Highest Diff "+prueba+";"+"Solved Score "+prueba+";"+"Entropy "+prueba+";"+"Steps "+prueba+";Fecha "+prueba+";TiempoDif "+prueba+";TiempoDif2 "+prueba+";TiempoTotal "+prueba+";";
	}

}
