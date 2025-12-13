package store.api.util;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil implements Serializable{

	private static final long serialVersionUID = 1L;

    public static final String FORMATO_DIA_MES_ANO = "dd/MM/yyyy";
    public static final String DATA1_MAIOR_QUE_DATA2 = "DATA1_MAIOR_QUE_DATA2";
    public static final String DATA1_MENOR_QUE_DATA2 = "DATA1_MENOR_QUE_DATA2";
    public static final String DATA1_IGUAL_OU_MAIOR_QUE_DATA2 = "DATA1_IGUAL_OU_MAIOR_QUE_DATA2";
    public static final String DATA1_IGUAL_OU_MENOR_QUE_DATA2 = "DATA1_IGUAL_OU_MENOR_QUE_DATA2";
    public static final String DATA1_IGUAL_A_DATA2 = "DATA1_IGUAL_A_DATA2";

	public static Integer getProximoDiaSemana(String nomeDiaSemana) {
		Locale locale = new Locale("pt", "BR"); // Para suportar nomes em português

		// Converter o nome do dia para um objeto DayOfWeek
		DayOfWeek diaSemanaProcurado = null;
		for (DayOfWeek dia : DayOfWeek.values()) {
			if (dia.getDisplayName(TextStyle.FULL, locale).equalsIgnoreCase(nomeDiaSemana)) {
				diaSemanaProcurado = dia;
				break;
			}
		}

		// Obter a data atual
		LocalDate hoje = LocalDate.now();

		// Verificar se o dia atual já é o dia procurado
		DayOfWeek diaAtual = hoje.getDayOfWeek();
		LocalDate proximoDia;

		if (diaAtual.equals(diaSemanaProcurado)) {
			proximoDia = hoje; // O dia é hoje
		} else {
			// Encontrar o próximo dia da semana
			proximoDia = hoje.with(TemporalAdjusters.nextOrSame(diaSemanaProcurado));
		}

		// Exibir o número do próximo dia do mês
		return proximoDia.getDayOfMonth();
	}

	public static void main(String[] args) {
		String nomeDiaSemana = "sábado"; // O nome do dia da semana fornecido
		Locale locale = new Locale("pt", "BR"); // Para suportar nomes em português

		// Converter o nome do dia para um objeto DayOfWeek
		DayOfWeek diaSemanaProcurado = null;
		for (DayOfWeek dia : DayOfWeek.values()) {
			if (dia.getDisplayName(TextStyle.FULL, locale).equalsIgnoreCase(nomeDiaSemana)) {
				diaSemanaProcurado = dia;
				break;
			}
		}

		if (diaSemanaProcurado == null) {
			System.out.println("Dia da semana inválido!");
			return;
		}

		// Obter a data atual
		LocalDate hoje = LocalDate.now();

		// Verificar se o dia atual já é o dia procurado
		DayOfWeek diaAtual = hoje.getDayOfWeek();
		LocalDate proximoDia;

		if (diaAtual.equals(diaSemanaProcurado)) {
			proximoDia = hoje; // O dia é hoje
		} else {
			// Encontrar o próximo dia da semana
			proximoDia = hoje.with(TemporalAdjusters.nextOrSame(diaSemanaProcurado));
		}

		// Exibir o número do próximo dia do mês
		System.out.println("O próximo " + nomeDiaSemana + " será no dia " + proximoDia.getDayOfMonth() + " do mês.");
	}

	public static List<LocalDate> obterDatasMesesSubtraidos(int quantidadeDeMeses) {
		// Lista para armazenar as datas
		List<LocalDate> datas = new ArrayList<>();

		// Obtém a data atual
		LocalDate hoje = LocalDate.now();

		// Adiciona a data atual à lista
		datas.add(hoje);

		// Adiciona as datas dos meses subtraídos
		for (int i = 1; i <= quantidadeDeMeses; i++) {
			// Subtrai um mês da data atual
			hoje = hoje.minusMonths(1);
			// Adiciona a data à lista
			datas.add(hoje);
		}

		// Retorna a lista de datas
		return datas;
	}

	public static List<Integer>  checkMes(List<Integer> mes){
		if(mes == null || mes.isEmpty()){
			mes = new ArrayList<>();
			Calendar instance = Calendar.getInstance();
			mes.add(instance.get(Calendar.MONTH)+1);
		}
		return mes;
	}
	public static List<Integer> checkAno(List<Integer> ano){
		if(ano == null || ano.isEmpty()){
			ano = new ArrayList<>();
			Calendar instance = Calendar.getInstance();
			ano.add(instance.get(Calendar.YEAR));
		}
		return ano;
	}

	public static String getDurationByTime(LocalDateTime inicio, LocalDateTime fim) {
		Duration duration = Duration.between(inicio, fim);
		if(duration.toMinutes() <=0){
			return String.format("%d segundos", duration.toSeconds());
		}else{
			return String.format("%d minutos", duration.toMinutes());
		}

	}

	public static String getDuration(LocalDateTime inicio, LocalDateTime fim) {
		Duration duration = Duration.between(inicio, fim);
		long horas = duration.toHours();
		long minutos = duration.toMinutes() % 60;
		long segundos = duration.getSeconds() % 60;

		return String.format("%d horas %d minutos e %d segundos", horas, minutos, segundos);
	}

	public static String toCalendarDate(String input){
		if(StringUtils.isEmpty(input)){
			return "";
		}
		DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate data = null;
		try {
			formatoEntrada = DateTimeFormatter.ofPattern("d/MM/yyyy");
			data = LocalDate.parse(input, formatoEntrada);
		} catch (Exception e) {
			return null;
		}
		return data.format(formatoSaida);
	}

	public static String getNomeMesCompleto(Integer month) {
		if (month == 1) {
			return "Janeiro";
		} else if (month == 2) {
			return "Fevereiro";
		}else if (month == 3) {
			return "Março";
		}else if (month == 4) {
			return "Abril";
		}else if (month == 5) {
			return "Maio";
		}else if (month == 6) {
			return "Junho";
		}else if (month == 7) {
			return "Julho";
		}else if (month == 8) {
			return "Agosto";
		}else if (month == 9) {
			return "Setembro";
		}else if (month == 10) {
			return "Outubro";
		}else if (month == 11) {
			return "Novembro";
		}else if (month == 12) {
			return "Dezembro";
		}
		return "";
	}

	public static String getNomeMes(Integer month) {
		if (month == 1) {
			return "Jan";
		} else if (month == 2) {
			return "Fev";
		}else if (month == 3) {
			return "Mar";
		}else if (month == 4) {
			return "Abr";
		}else if (month == 5) {
			return "Mai";
		}else if (month == 6) {
			return "Jun";
		}else if (month == 7) {
			return "Jul";
		}else if (month == 8) {
			return "Ago";
		}else if (month == 9) {
			return "Set";
		}else if (month == 10) {
			return "Out";
		}else if (month == 11) {
			return "Nov";
		}else if (month == 12) {
			return "Dez";
		}
		return "";
	}


	public static String getDataFaturaNuBank(String data) {
		if(StringUtils.isEmpty(data)){
			return null;
		}
		data = data.replaceAll(" ", "/")
				.replace("JAN","01")
				.replace("FEV","02")
				.replace("MAR","03")
				.replace("ABR","04")
				.replace("MAI","05")
				.replace("JUN","06")
				.replace("JUL","07")
				.replace("AGO","08")
				.replace("SET","09")
				.replace("OUT","10")
				.replace("NOV","11")
				.replace("DEZ","12");

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.parse(data);
		} catch (Exception e) {
			data = null;
		}
		return data;
	}

    
    public static boolean isDataTimePosterior(Date data, Date alvo){
		return  (data.getTime() > alvo.getTime());
	}
    
    public static Integer getDiaSemana(String dia) {
    	return getDiaSemana(stringToDate(dia));
    }
    public static Integer getDiaSemana(Date dia) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(dia);
		return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    public static String millisToTime(Long durationInMillis) {
		long second = (durationInMillis / 1000) % 60;
		long minute = (durationInMillis / (1000 * 60)) % 60;
		long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

		return  String.format("%02d:%02d:%02d", hour, minute, second);
	}
    
    public static String getNomeMesCompleto(int month) {
    	if (month == 1) {
    		return "Janeiro";
    	} else if (month == 2) {
    		return "Fevereiro";
    	}else if (month == 3) {
    		return "Março";
    	}else if (month == 4) {
    		return "Abril";
    	}else if (month == 5) {
    		return "Maio";
    	}else if (month == 6) {
    		return "Junho";
    	}else if (month == 7) {
    		return "Julho";
    	}else if (month == 8) {
    		return "Agosto";
    	}else if (month == 9) {
    		return "Setembro";
    	}else if (month == 10) {
    		return "Outubro";
    	}else if (month == 11) {
    		return "Novembro";
    	}else if (month == 12) {
    		return "Dezembro";
    	}
    	return "";
    }
    
    
    public static String convertMillis(long milliseconds){
 	   long seconds, minutes, hours;
 	   seconds = milliseconds / 1000;
 	   minutes = seconds / 60;
 	   seconds = seconds % 60;
 	   hours = minutes / 60;
 	   minutes = minutes % 60;
  	   return (hours < 9 ? "0" + hours : hours) +":" + (minutes < 9 ? "0" + minutes : minutes)  + ":" + (seconds < 9 ? "0" + seconds : seconds);
  	}
    
    public static Date formataData(Date data, String formato) {
		String value = dateToString(data,formato);
		return stringToDate1(value, formato);
	}
    
    public static int calculaIdade(String data) {
    	String[] partes = data.split("/");
    	String dia ="";
    	String mes = "";
    	if(partes[0].length() == 1){
    		dia = "0" + partes[0];
    	}else{
    		dia = partes[0];
    	}
    	if(partes[1].length() == 1){
    		mes = "0" + partes[1];
    	}else{
    		mes  = partes[1];
    	}
    	data = dia + "/" + mes + "/" + partes[2];
    	
		// Data de hoje.
		GregorianCalendar hoje = new GregorianCalendar();
		int diah = hoje.get(Calendar.DAY_OF_MONTH);
		int mesh = hoje.get(Calendar.MONTH) + 1;
		int anoh = hoje.get(Calendar.YEAR);

		// Data do nascimento.
		int dian = Integer.valueOf(data.substring(0,2));
		int mesn = Integer.valueOf(data.substring(3,5));
		int anon = Integer.valueOf(data.substring(6,10));

		// Idade.
		int idade;

		if (mesn < mesh || (mesn == mesh && dian <= diah))
		idade = anoh - anon;
		else
		idade = (anoh - anon)-1;

		return (idade);
	}
    
    public static String formatarUltimoAcesso(Date date){
    	if (date == null){
    		return "Nunca acessou";
    	}else{
			
			long difference = new Date().getTime() - date.getTime();

	        long x = difference / 1000;
	        long seconds = x % 60;
	        x /= 60;
	        long minutes = x % 60;
	        x /= 60;
	        long hours = x % 24;
	        x /= 24;
	        long days = x;
	        
	        return  days  + " Dias " + hours + " Horas " + minutes + " minutos";
    	}
    }
    
    public static Date colocarEmMeiaNoite(Date temp){
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
    
    public static Calendar getDataZerada(Date temp){
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
    
    public static Integer isMesmoDia(Date parametro, Date alvo){
		Calendar primeiro = getDataZerada(parametro);
		Calendar segundo = getDataZerada(alvo);
		if (primeiro.getTimeInMillis() == segundo.getTimeInMillis())
			return 0;
		return (primeiro.getTimeInMillis() > segundo.getTimeInMillis())?-1:1;
	}
    
    public static boolean isPeriodoValido(Date dataInicio, Date dataTermino){
    	if (dataInicio == null || dataTermino == null) {return false;}
		return (isDataAnterior(dataInicio, dataTermino) && isDataPosterior(dataTermino, dataInicio)) || isMesmoDia(dataInicio, dataTermino) == 0;
	
	}
    
    public static boolean isDataAnterior(Date data, Date alvo){
		return  (data.getTime() < alvo.getTime());
	}
    
    public static Date getApenasData(){
    	return getApenasData(new Date());
    }
    
    public static Date getApenasData(Date data){
		Calendar temporario = Calendar.getInstance();
		temporario.setTime(data);
		temporario.set(Calendar.HOUR_OF_DAY, 0);
		temporario.set(Calendar.MINUTE, 0);
		temporario.set(Calendar.SECOND, 0);
		temporario.set(Calendar.MILLISECOND, 0);
		return temporario.getTime();
	}
    
    public static boolean isDataPosterior(Date data, Date alvo){
    	data = getApenasData(data);
    	alvo = getApenasData(alvo);
		return  (data.getTime() > alvo.getTime());
	}
    
    public static String segundosToHoraMinuto(Long segundos) {
		return segundosToHoraMinuto(Integer.parseInt(segundos.toString()));
	}
	
    public static String segundosToHoraMinutoSegundo(Long segundos) {
		Calendar temporario = Calendar.getInstance();
		temporario.set(Calendar.HOUR_OF_DAY, 0);
		temporario.set(Calendar.MINUTE, 0);
		temporario.set(Calendar.SECOND, 0);
		temporario.set(Calendar.MILLISECOND, 0);
		temporario.set(Calendar.SECOND, segundos.intValue());
		return new SimpleDateFormat("HH:mm:ss").format(temporario.getTime());
	}
    
    public static String segundosToHoraMinuto(Integer segundos) {
		Calendar temporario = Calendar.getInstance();
		temporario.set(Calendar.HOUR_OF_DAY, 0);
		temporario.set(Calendar.MINUTE, 0);
		temporario.set(Calendar.SECOND, 0);
		temporario.set(Calendar.MILLISECOND, 0);
		temporario.set(Calendar.SECOND, segundos);
		return new SimpleDateFormat("HH:mm").format(temporario.getTime());
	}
    public static long getDiferencaEmSegundos(Date inicio, Date termino){
		try{
			long retorno = (long)(termino.getTime() - inicio.getTime())/1000;
			if (retorno < 0){
				return 0l;
			}
			return retorno;
		}catch(Throwable t){
			return 0l;
		}
	}
    
    public static List<Integer> getListaIdsMesEntreDatas(Date inicio, Date fim) {
    	List<Integer> lista = new ArrayList<Integer>();
    	
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(inicio);
    	
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(fim);
    	lista.add(c1.get(Calendar.MONTH));
    	
    	while(c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH)) {
    		
    		c1.add(Calendar.MONTH, 1);    		
    		lista.add(c1.get(Calendar.MONTH));	
    	}
    	
    	return lista;
    }
    
    public static List<String> getListaNomeMesEntreDatas(Date inicio, Date fim) {
    	List<String> lista = new ArrayList<String>();
    	
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(inicio);
    	
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(fim);
    	lista.add(getNomeMes(inicio));
    	
    	while(c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH)) {
    		
    		c1.add(Calendar.MONTH, 1);    		
    		lista.add(getNomeMes(c1.getTime()));	
    		
    	}
      
    	return lista;
    	
    }
    
    public static String getDiferencaDatasEmDiasHorasMinutos(Long dataAntiga) {
    	long difference = new Date().getTime() - dataAntiga;

        long x = difference / 1000;
        long seconds = x % 60;
        x /= 60;
        long minutes = x % 60;
        x /= 60;
        long hours = x % 24;
        x /= 24;
        long days = x;
        
        return days + " Dias " + hours + " Horas " + minutes + " minutos";
    }
    
    private static Date stringToDate1(String dataString) {
		return stringToDate(dataString,"dd/MM/yyyy");
	}
	
    public static Date formataDateTime(Date data) {
		String value = dateTimeSecondToString(data);
		return stringToDateTimeSecond(value);
	}

	public static String convertToPTBr(String inputDate) {
		// Definir o formato da data de entrada
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Converter a string para um objeto LocalDate
		LocalDate date = LocalDate.parse(inputDate, inputFormatter);

		// Definir o formato desejado de saída
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Retornar a data formatada como string
		return date.format(outputFormatter);
	}

    public static Date formataData(Date data) {
		String value = dateToString(data);
		return stringToDate1(value);
	}
    
    private static Date stringToDate1(String dataString, String formato) {
		try {
    		
    		DateFormat df = new SimpleDateFormat (formato);  
    		    
			return new Date(df.parse(dataString).getTime());
			
		} catch (ParseException e) {
		}
		return null;
    }
	
    public static Boolean isDateBetween(String dataInicial, String dataFinal, Date verificacao){
    	Date data1 = stringToDate(dataInicial);
    	Date data2 = stringToDate(dataFinal);
    	return isDateBetween(data1, data2, verificacao);
    }
    
    public static Boolean isDateTimeBetween(Date dataInicial, Date dataFinal, Date verificacao){
    	
    	Date data1 = stringToDateTimeSecond(dateTimeSecondToString(dataInicial));
		Date data2 = stringToDateTimeSecond(dateTimeSecondToString(dataFinal));

		Calendar c = Calendar.getInstance();
		Date dataAtual = formataDateTime(verificacao);

		if (data1.compareTo(dataAtual) == 0 || data2.compareTo(dataAtual) == 0) {
			return true;
		}

		if (data1.compareTo(dataAtual) > 0 || data2.compareTo(dataAtual) < 0) {
			return false;
		}
		return true;
    }

    public static Boolean isDateBetween(Date dataInicial, Date dataFinal, Date verificacao){
    	
    	Date data1 = stringToDate1(dateToString(dataInicial));
		Date data2 = stringToDate1(dateToString(dataFinal));

		Calendar c = Calendar.getInstance();
		Date dataAtual = formataData(c.getTime());

		if (data1.compareTo(dataAtual) == 0 || data2.compareTo(dataAtual) == 0) {
			return true;
		}

		if (data1.compareTo(dataAtual) > 0 || data2.compareTo(dataAtual) < 0) {
			return false;
		}
		return true;
    }

    public static Date getDataPeriodo(Date dataInicio, Date dataFim, Integer diaSemana){
    	Calendar di = Calendar.getInstance();
    	di.setTime(dataInicio);
    	
    	Calendar df = Calendar.getInstance();
    	df.setTime(dataFim);
    	df.add(Calendar.DATE, 1);
    	
    	
    	while(di.before(df)) {
    		
    		Integer w = di.get(Calendar.DAY_OF_WEEK);
    		if(w.equals(diaSemana)) {
    			return di.getTime();
    		}
    		di.add(Calendar.DATE, 1);
    	}
    	
    	return null;
    }
    
    public static Date formataTempo(String tempo){
    	String[] tempos = tempo.split(":");
    	Calendar c = Calendar.getInstance();
    	c.set(2014, 1, 1, Integer.parseInt(tempos[0]), Integer.parseInt(tempos[1]), Integer.parseInt(tempos[2]));
    	return c.getTime();
    }
    
    public static Boolean equals(Date data1, Date data2){
    	return dateToString(data1).equals(dateToString(data2));
    }
    
    public static Date somaSegundo(Date tempo, Integer second){
    	
    	Calendar c = Calendar.getInstance();
    	c.setTime(tempo);
    	c.add(Calendar.SECOND, second);
    	return c.getTime();
    }

    public static Date somaMinuto(Date tempo, Integer minutos){
    	
    	Calendar c = Calendar.getInstance();
    	c.setTime(tempo);
    	c.add(Calendar.MINUTE, minutos);
    	return c.getTime();
    }
    
    public static String somarHoras(String hora1,String hora2){
		
    	String hrs[] = hora1.split(":");
		Integer somaHora1 = (Integer.parseInt(hrs[0])*60)+Integer.parseInt(hrs[1]);
		
		hrs = hora2.split(":");
		Integer somaHora2 = (Integer.parseInt(hrs[0])*60)+Integer.parseInt(hrs[1]);
		
		Integer totalHoras = (somaHora1+somaHora2)/60;
		
		Integer totalMinutos = ((somaHora1+somaHora2)%60);
		
		StringBuilder r = new StringBuilder();
		
		String totalH;
		if (totalHoras==0){
			totalH = "00";
		}else{
			totalH = totalHoras.toString();
		}
		
		if (totalMinutos < 10 ){
			r.append(totalH)
			.append(":0")
			.append(totalMinutos.toString());
		}else{
			r.append(totalH)
		    .append(":")
		    .append(totalMinutos.toString());
		}
		
		if (r.length()==3){
			r.append("0");
		}
		
		return r.toString();
	}

    public static Integer getUltimoDiaMes(Integer ano, Integer mes){
    	mes -=1;
    	Calendar cal = new GregorianCalendar(ano, mes, 1);
    	return cal.getMaximum(Calendar.DAY_OF_MONTH);
    }
    
    
    /**
     * Calcula quantas horas e minutos tem entre duas datas
     * 
     * @param dataInicio	data inicial
     * @param dataFim		data final
     * @return			    String no formato hh:mm
     */
    public static String getHorasMinutosEntreDatas(Date dataInicio, Date dataFim){
    	float differenceMilliSeconds = dataFim.getTime() - dataInicio.getTime();
    	
    	float tempoM= (differenceMilliSeconds/1000f/60f/60f)*60;   
	    int hora=0;   
	    int minutos=0;   
	    while(tempoM>=60){   
	        hora++;   
	        tempoM=tempoM-60;           
	    }   
	    minutos=(int)tempoM;   
	    String horaString = String.valueOf(hora);
	    String minutosString = String.valueOf(minutos);
	    
	    if (hora <=9) {
	    	horaString = "0" + String.valueOf(hora);
	    }
	    if (minutos <=9) {
	    	minutosString = "0" + String.valueOf(minutos);
	    }
	    return horaString + ":" + minutosString; 
	    
    }
    
    public static Integer getDiasEntreDatasSemSabadoDomingo(Date dataMaisAntiga, Date dataMaisNova) {
        String data1 = dateToString(dataMaisAntiga);
        String data2 = dateToString(dataMaisNova);
        return getDiasEntreDatasSemSabadoDomingo(data1,data2);
    }
    
    public static Integer getDiasEntreDatasSemSabadoDomingo(String dataMaisAntiga, String dataMaisNova) {
        String data1 = dataMaisAntiga;
        String data2 = dataMaisNova;
        int dias = 0;
        while (compararDatas(data1, data2, DATA1_MENOR_QUE_DATA2)) {
        	
        	Date d = DateUtil.transformarStringEmDate(data1,"dd/MM/yyyy");
            data1 = proximoDia(data1);
            if (getNomeDiaSemanaAbr(d).equalsIgnoreCase("Sáb") || getNomeDiaSemanaAbr(d).equalsIgnoreCase("dom")){
            	continue;
            }
            dias++;
        }
        return dias;
    }
    
    public static Long getNumeroDiasEntreDatas(Date dataInicio, Date dataFim ){
    	
//  	  Mostra diferen�a em ms:  
//      System.out.println("diferen�a em ms: " + diferenca);  
//      Mostra diferen�a em s:  
//      System.out.println("diferen�a em s: " + diferenca / 1000);  
//      Mostra diferen�a em m:  
//      System.out.println("diferen�a em m: " + diferenca / 1000 / 60);  
//      Mostra diferen�a em h:  
//      System.out.println("diferen�a em h: " + diferenca / 1000 / 60 / 60);  
//      Mostra diferen�a em dias:  
//      System.out.println("diferen�a em dias: " + diferenca / 1000 / 60 / 60 / 24);  
         
    	long tempo = dataFim.getTime() - dataInicio.getTime();
  	  
  		return tempo / 1000 / 60 / 60 / 24;
    }
    
    public static Long getNumeroMinutosEntreDatas(Date dataInicio, Date dataFim ){
    	
//  	  Mostra diferen�a em ms:  
//      System.out.println("diferen�a em ms: " + diferenca);  
//      Mostra diferen�a em s:  
//      System.out.println("diferen�a em s: " + diferenca / 1000);  
//      Mostra diferen�a em m:  
//      System.out.println("diferen�a em m: " + diferenca / 1000 / 60);  
//      Mostra diferen�a em h:  
//      System.out.println("diferen�a em h: " + diferenca / 1000 / 60 / 60);  
//      Mostra diferen�a em dias:  
//      System.out.println("diferen�a em dias: " + diferenca / 1000 / 60 / 60 / 24);  
         
  	long tempo = dataFim.getTime() - dataInicio.getTime();
  	  
  	return tempo / 1000 / 60;
  }
    
    public static Long getNumeroHorasEntreDatas(Date dataInicio, Date dataFim ){
    	
//    	  Mostra diferen�a em ms:  
//        System.out.println("diferen�a em ms: " + diferenca);  
//        Mostra diferen�a em s:  
//        System.out.println("diferen�a em s: " + diferenca / 1000);  
//        Mostra diferen�a em m:  
//        System.out.println("diferen�a em m: " + diferenca / 1000 / 60);  
//        Mostra diferen�a em h:  
//        System.out.println("diferen�a em h: " + diferenca / 1000 / 60 / 60);  
//        Mostra diferen�a em dias:  
//        System.out.println("diferen�a em dias: " + diferenca / 1000 / 60 / 60 / 24);  
           
    	long tempo = dataFim.getTime() - dataInicio.getTime();
    	  
    	return tempo / 1000 / 60 / 60;
    }
    
    /**
     * Retorna o dia da semana representado por um inteiro
     * 
     * @param data   Data a ser avaliada
     * @return		 Retorna o numero inteiro representativo da semana sendo
     *               7 = sabado
     *               1 = domingo
     *               2 = segunda
     *               3 = quarta
     *               4 = quinta
     *               5 = sexta
     *               
     */
    public static Integer getNumeroDiaSemana(Date data) {
    	Calendar cal = Calendar.getInstance();  
		cal.setTime(data);
		return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * Retorna o dia da semana por extenso abreviado em 3 caracteres.
     * 
     * @param date		Objeto date
     * @return			Retorna dia da semana por extenso, Exemplo: Seg, Ter
     */
    public static String getNomeDiaSemanaAbr(Date date) {
    	Locale.setDefault (new Locale ("pt", "BR"));  
        DateFormat df = new SimpleDateFormat ("E");  
        return df.format (date); 
    }
    
    /**
     * Retorna o dia da semana por extenso.
     * 
     * @param date		Objeto date
     * @return			Retorna dia da semana por extenso, Exemplo: Segunda-feira
     */
	public static String getNomeDiaSemanaAbr(String date) {
		Date date1 = stringToDate(date);
		return getNomeDiaSemanaAbr(date1);
	}

	public static String getNomeDiaSemana(Date date) {
    	Locale.setDefault (new Locale ("pt", "BR"));  
        DateFormat df = new SimpleDateFormat ("EEEE");  
        return df.format (date); 
    }
    
    public static String getCurrentDate(String formato) {
    	SimpleDateFormat sdf = new SimpleDateFormat(formato);
    	return sdf.format(new Date());
    }
    
    public static String getCurrentDateTimeSecondAsHash() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	String valor = sdf.format(new Date());
		return valor.replace("/", "").replace(" ", "").replace(":", "");
    }
    
    public static String getCurrentDateTimeSecond() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	return sdf.format(new Date());
    }
    
    
    public static String getCurrentDateTime() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	return sdf.format(new Date());
    }
    
    public static String getCurrentTimeSecond() {
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	return sdf.format(new Date());
    }
    
    public static String getCurrentDate() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	return sdf.format(new Date());
    }
    
    public static String dateTimeSecondToString(Date d){
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
	    return sd.format(d);
	}
    
    public static String timeSecondToString(Date d){
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
	    return sd.format(d);
	}
    
    public static String timeToString(Date d){
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
	    return sd.format(d);
	}
    public static String dateTimeToString(Date d){
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    return sd.format(d);
	}

	public static Date ultimoDiaDoMes() {
		LocalDate ultimoDia = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
		return Date.from(ultimoDia.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date primeiroDiaDoMes() {
		LocalDate primeiroDia = LocalDate.now().withDayOfMonth(1);
		return Date.from(primeiroDia.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
    
    public static String dateToString(Date d, String formato){
		if(d == null) return null;
		SimpleDateFormat sd = new SimpleDateFormat(formato);
	    return sd.format(d);
	}

	public static String dateToString(Date d){
		return dateToString(d, "dd/MM/yyyy");	    
	}
	
    /**
     * Retorna a diferen�a entre horas passadas como par�metro no formato hh:mm:ss
     * horaHHmmss_1 - horaHHmmss_2
     * @param horaHHmmss_1
     * @param horaHHmmss_2
     * @return hora no formato hh:mm:ss
     */
    public static String subtrairHorasHHmmss(String horaHHmmss_1, String horaHHmmss_2) {
        StringBuilder horaFinal = new StringBuilder();
        int sub = 0;
        int subHoras = 0;
        int subMinutos = 0;

        String horas = "00";
        String minutos = "00";
        String segundos = "00";

        int segundos1 = 0;
        int segundos2 = 0;
        try {
            segundos1 = (Integer.parseInt(horaHHmmss_1.substring(0, 2)) * 3600) + (Integer.parseInt(horaHHmmss_1.substring(3, 5)) * 60) + Integer.parseInt(horaHHmmss_1.substring(6, 8));
            segundos2 = (Integer.parseInt(horaHHmmss_2.substring(0, 2)) * 3600) + (Integer.parseInt(horaHHmmss_2.substring(3, 5)) * 60) + Integer.parseInt(horaHHmmss_2.substring(6, 8));
        } catch (Exception e) {
            System.out.println("Erro ao tentar converter a hora para segundos. Verifique o formato das horas do par�metro.");
        }
        if (segundos1 > segundos2) {
            sub = segundos1 - segundos2;
        } else if (segundos2 > segundos1) {
            sub = segundos2 - segundos1;
        } else {
            sub = 0;
        }

        if (sub >= 3600) {
            subHoras = (sub - (sub % 3600)) / 3600;
            sub = sub - (subHoras * 3600);
            if (subHoras < 10) {
                horas = "0" + Integer.toString(subHoras);
            } else {
                horas = Integer.toString(subHoras);
            }
        }
        horaFinal.append(horas).append(":");

        if (sub >= 60) {
            subMinutos = (sub - (sub % 60)) / 60;
            sub = sub - (subMinutos * 60);
            if (subMinutos < 10) {
                minutos = "0" + Integer.toString(subMinutos);
            } else {
                minutos = Integer.toString(subMinutos);
            }
        }
        horaFinal.append(minutos).append(":");

        if (sub < 10) {
            segundos = "0" + Integer.toString(sub);
        } else {
            segundos = Integer.toString(sub);
        }
        horaFinal.append(segundos);
        return horaFinal.toString();
    }
    /**
     * Retorna a data correspondente  � soma (ou substraeração se o par�metro dias for negativo) dos dias passados no par�metro dias.
     * @param dd_mm_aaaa
     * @param dias
     * @return String dd_mm_aaaa
     */
    public static String somarDiasAData(String dd_mm_aaaa, int dias) {
        return transformarDateEmString(somaDias(transformarStringEmDate(dd_mm_aaaa, FORMATO_DIA_MES_ANO), dias), FORMATO_DIA_MES_ANO);
    }

    /**
     * Soma meses a data  
     * @param data   data a ser manipulada 
     * @param meses   numero de meses
     * @return Date data com o novo mes
     */
    public static Date somaMeses(Date data, Integer meses) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(data.getTime());
        cal.add(Calendar.MONTH, meses);
        return cal.getTime();
    }
    
    public static Date curDateWithDays(Integer dias){
    	if (dias > 0) {
    		return somaDias(new Date(), dias);
    	} else {
    		return subtraiDias(new Date(), dias);
    	}
    }
    
    public static Date somaDias(Date data, String dias) {
    	return somaDias(data, Integer.parseInt(dias));
    }
    
    public static Date subtraiDias(Date data, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(data.getTime());
        cal.add(Calendar.DATE, -dias);
        return cal.getTime();
    }
    
    /**
     * Retorna a data correspondente  � soma (ou substraeração se o par�metro dias for negativo) dos dias passados no par�metro dias.
     * @param data
     * @param dias
     * @return Date
     */
    public static Date somaDias(Date data, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(data.getTime());
        cal.add(Calendar.DATE, dias);
        return cal.getTime();
    }

    public static Date stringToDate(String dataString, String formato) {
		return  transformarStringEmDate(dataString, formato);
    }
    
    public static Date stringToDateTimeSecond(String dataString) {
		return  transformarStringEmDate(dataString, "dd/MM/yyyy HH:mm:ss");
    }
    
    public static Date stringToDate(String dataString) {
		return  transformarStringEmDate(dataString, "dd/MM/yyyy");
    }
    
    /**
     * Transfora a String passada como par�metro em objeto Date
     * @param dataString
     * @param formato
     * @return Date
     */
    public static Date transformarStringEmDate(String dataString, String formato) {
    	try {
    		
    		DateFormat df = new SimpleDateFormat (formato);  
		    df.setLenient (false);
    		    
			return new Date(df.parse(dataString).getTime());
			
		} catch (ParseException e) {
		}
		return null;
    }

    /**
     * Transforma um objeto Date em uma String
     * @param date
     * @param formato
     * @return String
     */
    public static String transformarDateEmString(Date date, String formato) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(formato);
            return format.format(date);
        }
        return "";
    }

    /**
     * Retorna o número de dias entre as datas.
     * A data dataMaisAntiga tem que ser mais antiga do que dataMaisNova.
     * Se a data dataMaisAntiga não for mais antiga do que dataMaisNova, retorna 0.
     * @param dataMaisAntiga
     * @param dataMaisNova
     * @return 
     */
    public static int getDiasEntreDatas(String dataMaisAntiga, String dataMaisNova) {
        String data1 = dataMaisAntiga;
        String data2 = dataMaisNova;
        int dias = 0;
        while (compararDatas(data1, data2, DATA1_MENOR_QUE_DATA2)) {
            dias++;
            data1 = proximoDia(data1);
//            if (dias > 1000) {
//                break;
//            }
        }
        return dias;
    }
    

    /**
     * Comparaeração de datas. Use as vari  veis estáticas da classe Data no par�metro 'comparacao'.
     * Ex: Data.DATA1_MAIOR_QUE_DATA2
     * @param data_dd_mm_aaaa_1
     * @param data_dd_mm_aaaa_2
     * @param comparacao
     * @return boolean
     */
    public static boolean compararDatas(String data_dd_mm_aaaa_1, String data_dd_mm_aaaa_2, String comparacao) {

        String data_AAAAmmdd_hhmmss_1 = data_dd_mm_aaaa_1.substring(6, 10) + data_dd_mm_aaaa_1.substring(3, 5) + data_dd_mm_aaaa_1.substring(0, 2);
        String data_AAAAmmdd_hhmmss_2 = data_dd_mm_aaaa_2.substring(6, 10) + data_dd_mm_aaaa_2.substring(3, 5) + data_dd_mm_aaaa_2.substring(0, 2);
        int data1 = Integer.parseInt(data_AAAAmmdd_hhmmss_1);
        int data2 = Integer.parseInt(data_AAAAmmdd_hhmmss_2);

        if (comparacao.equals(DATA1_MAIOR_QUE_DATA2)) {
            if (data1 > data2) {
                return true;
            }
        }
        if (comparacao.equals(DATA1_MENOR_QUE_DATA2)) {
            if (data1 < data2) {
                return true;
            }
        }
        if (comparacao.equals(DATA1_IGUAL_OU_MAIOR_QUE_DATA2)) {
            if (data1 >= data2) {
                return true;
            }
        }
        if (comparacao.equals(DATA1_IGUAL_OU_MENOR_QUE_DATA2)) {
            if (data1 <= data2) {
                return true;
            }
        }
        if (comparacao.equals(DATA1_IGUAL_A_DATA2)) {
            if (data1 == data2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna a pr�xima data no formato dd/mm/aaaa.
     * @param dd_mm_aaaa
     * @return String dd/mm/aaaa
     */
    public static String proximoDia(String dd_mm_aaaa) {
        int ano = Integer.parseInt(dd_mm_aaaa.substring(6, 10));
        int mes = Integer.parseInt(dd_mm_aaaa.substring(3, 5)) - 1;
        int dia = Integer.parseInt(dd_mm_aaaa.substring(0, 2));
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(ano, mes, dia + 1);
        StringBuilder dataString = new StringBuilder();
        String valor = getNumeroDeData(cal.get(Calendar.DATE));
        dataString.append(valor).append("/");
        valor = getNumeroDeData(cal.get(Calendar.MONTH) + 1);
        dataString.append(valor).append("/");
        valor = getNumeroDeData(cal.get(Calendar.YEAR));
        dataString.append(valor);
        return dataString.toString();
    }

    /**
     * Retorna uma string no formato 01 se o número for menor do que 10
     * @param numero
     * @return String
     */
    private static String getNumeroDeData(int numero) {
        if (numero < 10) {
            return "0" + String.valueOf(numero);
        } else {
            return String.valueOf(numero);
        }
    }
    
    public static Integer getAnoData(Date data) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(data);
    	return cal.get(Calendar.YEAR);
    }
    
    /**
     * Retorna o dia e mes da data no formato 01/09
     * 
     * @param data 
     * @return
     */
    public static String getDiaMesData(Date data) {
    	Integer dia = getDiaData(data);
    	Integer mes = getMesData(data);
    	String diaString = dia.toString();
    	String mesString = mes.toString();
    	
    	if (dia <=9) {
    		diaString = "0" + dia;
    	}
    	if (mes <=9) {
    		mesString = "0" + mes;
    	}
    	return diaString + "/" + mesString;
    }
    
    /**
     * Retorna o dia da data
     * 
     * @param data 
     * @return
     */
    public static Integer getDiaData(Date data) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(data);
    	return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Retorna o mes da data
     * 
     * @param data 
     * @return
     */
    public static Integer getMesData(Date data) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(data);
    	return cal.get(Calendar.MONTH) + 1;
    }
    
    /**
     * Retorna o nome do mes considera do o mes 0 como Janeiro 
     * 
     * @param data  data avaliada
     * @return		Nome do mes completo Exemplo: Janeiro, Fevereiro
     */
    public static String getNomeMesCompleto(Date data) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(data);
    	int month = cal.get(Calendar.MONTH);
    	if (month == 0) {
    		return "Janeiro";
    	} else if (month == 1) {
    		return "Fevereiro";
    	}else if (month == 2) {
    		return "Março";
    	}else if (month == 3) {
    		return "Abril";
    	}else if (month == 4) {
    		return "Maio";
    	}else if (month == 5) {
    		return "Junho";
    	}else if (month == 6) {
    		return "Julho";
    	}else if (month == 7) {
    		return "Agosto";
    	}else if (month == 8) {
    		return "Setembro";
    	}else if (month == 9) {
    		return "Outubro";
    	}else if (month == 10) {
    		return "Novembro";
    	}else if (month == 11) {
    		return "Dezembro";
    	}
    	return "";
    }

    /**
     * Retorna o nome do mes dado em string com tres letras
     * 
     * @param data  data avaliada
     * @return		Nome do mes com tres letras
     */
    public static String getNomeMes(Date data) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(data);
    	int month = cal.get(Calendar.MONTH);
    	if (month == 0) {
    		return "Jan";
    	} else if (month == 1) {
    		return "Fev";
    	}else if (month == 2) {
    		return "Mar";
    	}else if (month == 3) {
    		return "Abr";
    	}else if (month == 4) {
    		return "Mai";
    	}else if (month == 5) {
    		return "Jun";
    	}else if (month == 6) {
    		return "Jul";
    	}else if (month == 7) {
    		return "Ago";
    	}else if (month == 8) {
    		return "Set";
    	}else if (month == 9) {
    		return "Out";
    	}else if (month == 10) {
    		return "Nov";
    	}else if (month == 11) {
    		return "Dez";
    	}
    	return "";
    }
    
    /**
     * Retorna o numero de meses entre datas
     * 
     * @param a  Data inicio 
     * @param b  Data termino
     * @return   Retorna o numero de meses entre datas.
     */
    public static Integer getMesesEntreDatas(Date a , Date b ){
    	Calendar inicial = Calendar.getInstance();
    	Calendar termino = Calendar.getInstance();
    	inicial.setTime(a);
    	termino.setTime(b);

    	int count = 0;

    	while (inicial.get(Calendar.MONTH) < termino.get(Calendar.MONTH)){
    	    inicial.add(Calendar.MONTH, 1);
    	    count ++;
    	}

    	return ++count;

	}
    /**
     * Retorna o número de dias entre as datas.
     * A data dataMaisAntiga tem que ser mais antiga do que dataMaisNova.
     * Se a data dataMaisAntiga não for mais antiga do que dataMaisNova, retorna 0.
     * @param dataMaisAntiga
     * @param dataMaisNova
     * @return int 
     */
    public static Integer getDiasEntreDatas(Date dataMaisAntiga, Date dataMaisNova) {
        String data1 = transformarDateEmString(dataMaisAntiga, FORMATO_DIA_MES_ANO);
        String data2 = transformarDateEmString(dataMaisNova, FORMATO_DIA_MES_ANO);
        int dias = 0;
        while (compararDatas(data1, data2, DATA1_MENOR_QUE_DATA2)) {
            dias++;
            data1 = proximoDia(data1);
//            if (dias > 1000) {
//                break;
//            }
        }
        return dias;
    }
    
    public static String segundoToHoraMinutoSegundo(int seconds) {

    	if(seconds == 0){
    		return "00:00:00";
    	}
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    private static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }
    
    public static Integer horaMinutoSegundoToSegundo(String value){
    	String values[] = value.split(":");
    	Integer segundos = 0;
    	
    	int hora = Integer.parseInt(values[0]);
    	int minuto = Integer.parseInt(values[1]);
    	int segundo = Integer.parseInt(values[2]);
    	
    	if (hora > 0) {
    		segundos+= hora * 60 * 60;
    	}
    	
    	if (minuto > 0) {
    		segundos+= minuto * 60;
    	}
    	
    	if (segundo > 0) {
    		segundos+= segundo;
    	}
		return segundos;
    }
    
    public static String getSeconds(Date data){
		if (data == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String tempo = df.format(data);
		return tempo.split(":")[2];
	}
    
    public static String getHour(Date data){
		if (data == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String tempo = df.format(data);
		return tempo.split(":")[0];
	}
    
    public static String getMinutes(Date data){
		if (data == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String tempo = df.format(data);
		return tempo.split(":")[1];
	}
    
    public static String getTime(Date data){
		if (data == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(data);
	}
	
	public static String getDateInsertSqlServer(Date data){
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		return df.format(data);
	}
	
	public static String getDateInsertOracle(Date data){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return "TO_date('" + df.format(data) + "','DD/MM/YYYY')";
	}
	
	public static String getDateTimeInsertOracle(Date data){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		return "TO_date('" + df.format(data) + "','DD/MM/YYYY HH24:MI:SS')";
	}

	public static String formataData(String data, String formato) {
		Date d = stringToDate(data);
		return dateToString(d,formato);
	}

	public static String toNumber(String value){
		if(value.toLowerCase().contains(" jan ")){
			value = value.toLowerCase().replace(" jan ","/01/");
		}
		if(value.toLowerCase().contains(" fev ")){
			value = value.toLowerCase().replace(" fev ","/02/");
		}
		if(value.toLowerCase().contains(" mar ")){
			value = value.toLowerCase().replace(" mar ","/03/");
		}
		if(value.toLowerCase().contains(" abr ")){
			value = value.toLowerCase().replace(" abr ","/04/");
		}
		if(value.toLowerCase().contains(" mai ")){
			value = value.toLowerCase().replace(" mai ","/05/");
		}
		if(value.toLowerCase().contains(" jun ")){
			value = value.toLowerCase().replace(" jun ","/06/");
		}
		if(value.toLowerCase().contains(" jul ")){
			value = value.toLowerCase().replace(" jul ","/07/");
		}
		if(value.toLowerCase().contains(" ago ")){
			value = value.toLowerCase().replace(" ago ","/08/");
		}
		if(value.toLowerCase().contains(" set ")){
			value = value.toLowerCase().replace(" set ","/09/");
		}
		if(value.toLowerCase().contains(" out ")){
			value = value.toLowerCase().replace(" out ","/10/");
		}
		if(value.toLowerCase().contains(" nov ")){
			value = value.toLowerCase().replace(" nov ","/11/");
		}
		if(value.toLowerCase().contains(" dez ")){
			value = value.toLowerCase().replace(" dez ","/12/");
		}
		value = value.toLowerCase().replace("jan","01");
		value = value.toLowerCase().replace("fev","02");
		value = value.toLowerCase().replace("mar","03");
		value = value.toLowerCase().replace("abr","04");
		value = value.toLowerCase().replace("mai","05");
		value = value.toLowerCase().replace("jun","06");
		value = value.toLowerCase().replace("jul","07");
		value = value.toLowerCase().replace("ago","08");
		value = value.toLowerCase().replace("set","09");
		value = value.toLowerCase().replace("out","10");
		value = value.toLowerCase().replace("nov","11");
		value = value.toLowerCase().replace("dez","12");
		return value;
	}
	public static String consertarDataLinha(String linha){
		linha = DateUtil.toNumber(linha);
		Pattern padraoData = Pattern.compile("^(\\d{2}/\\d{2})([^/\\s])"); // 01/12
		Matcher matcher = padraoData.matcher(linha);
		if (matcher.find()) {
			linha = matcher.group(1) + " " + linha.substring(matcher.end(1));
		}
		padraoData = Pattern.compile("^(\\d{2}/\\d{2}/\\d{4})([^\\s])"); // 01/12
		matcher = padraoData.matcher(linha);
		if (matcher.find()) {
			linha = matcher.group(1) + " " + linha.substring(matcher.end(1));
		}
		padraoData = Pattern.compile("^(\\d{2}-\\d{2}-\\d{4})(?!\\s)"); // 01/12
		matcher = padraoData.matcher(linha);
		if (matcher.find()) {
			linha = matcher.group(1) + " " + linha.substring(matcher.end(1));
		}
		padraoData = Pattern.compile("^(\\d{2}-\\d{2})(?!-\\d{4})(?!\\s)"); // 01/12
		matcher = padraoData.matcher(linha);
		if (matcher.find()) {
			linha = matcher.group(1) + " " + linha.substring(matcher.end(1));
		}
		padraoData = Pattern.compile("(\\d{2})\\s([A-Z]{3})"); // 01/12
		matcher = padraoData.matcher(linha);
		if (matcher.find()) {
			linha = matcher.group(1) + " " + linha.substring(matcher.end(1));
		}

		String[] dados = linha.split(" ");
		if(dados.length > 0) {
			String data = DateUtil.toCalendar(dados[0]);
			if (data != null) {
				if(isValidDate(data)) {
					linha=data + " ";
					for (int i = 1; i < dados.length; i++) {
						linha += dados[i] + " ";
					}
				}
			}
		}
		return linha;
	}

	public static String toCalendar(String value) {

		try {
			if(value == null) {
				return null;
			}
			if(value.length() == 5){
				value+= value.substring(2,3) + Year.now().toString();
			}
			value = value.toLowerCase().replace("jan","01");
			value = value.toLowerCase().replace("fev","02");
			value = value.toLowerCase().replace("mar","03");
			value = value.toLowerCase().replace("abr","04");
			value = value.toLowerCase().replace("mai","05");
			value = value.toLowerCase().replace("jun","06");
			value = value.toLowerCase().replace("jul","07");
			value = value.toLowerCase().replace("ago","08");
			value = value.toLowerCase().replace("set","09");
			value = value.toLowerCase().replace("out","10");
			value = value.toLowerCase().replace("nov","11");
			value = value.toLowerCase().replace("dez","12");

			value= value.replace("/", "-");
			value = value.substring(value.length()- 4) + "-" + value.substring(3,5) + "-" + value.substring(0,2);
		} catch (Exception e) {
			return null;
		}
		return value;
	}

	public static boolean isValidDate(String date) {
		// Definindo o padrão da expressão regular para o formato YYYY-MM-DD
		String regex = "^(\\d{4})-(\\d{2})-(\\d{2})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);

		if (matcher.matches()) {
			int year = Integer.parseInt(matcher.group(1));
			int month = Integer.parseInt(matcher.group(2));
			int day = Integer.parseInt(matcher.group(3));

			// Verificando se a data é válida
			if (month < 1 || month > 12) {
				return false;
			}
			if (day < 1 || day > 31) {
				return false;
			}
			if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
				return false;
			}
			if (month == 2) {
				if (isLeapYear(year)) {
					if (day > 29) {
						return false;
					}
				} else {
					if (day > 28) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	// Método auxiliar para verificar se um ano é bissexto
	private static boolean isLeapYear(int year) {
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				return year % 400 == 0;
			}
			return true;
		}
		return false;
	}

    public static Date getDateMinus(int i) {
		Date dataAtual = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataAtual);
		calendar.add(Calendar.DAY_OF_MONTH, -i);
		return calendar.getTime();
    }

	public static String converterParaPtBr(String data) {
		OffsetDateTime dateTime = OffsetDateTime.parse(
				data,
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
		);

		DateTimeFormatter formatterPtBr =
				DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));

		return dateTime.format(formatterPtBr);
	}

}
