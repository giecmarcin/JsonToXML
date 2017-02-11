public class Cutter {
    public Cutter() {
    }

    public String cutText(String w, String idKlucz) {
        //System.out.println(w);
        String zm = w;
        String klucz = idKlucz;
        String wynik = zm.replaceAll("\\[", "");
        wynik = wynik.replaceAll("\\]", "");
        wynik = wynik.replaceAll("\"", "");
        String[] split = wynik.split(",");
        String koniec = "";
        for (String s : split) {
            // koniec="<"+klucz+">";
            if (s.contains("{")) {
                s = s.replaceAll("\\{", "");
                s = s.replaceAll("\\}", "");
                String[] obiekt = s.split(":");
                koniec += "<" + klucz + ">\n" + "\t<" + obiekt[0].trim() + ">" + obiekt[1].trim() + "</" + obiekt[0].trim() + ">\n</" + klucz + ">\n";
                // klucz += "</"+klucz+">\n";
            } else {
                koniec += "<" + klucz + ">" + s.trim() + "</" + klucz + ">\n";
                // klucz += "</"+klucz+">\n";
            }


        }
        return koniec;
    }

    public String cutText2(String w, String idKlucz) {
        String koniec = "";
        try {
            String finalny = "";
            String zm = w;
            //System.out.println(zm);
            String[] split = zm.split("]");

            //System.out.println(split[0]);
            for (int k = 0; k < split.length; k++) {
                int idDoGlownegoKlucza = split[0].indexOf("[");
                String glownyKlucz = split[k].substring(0, idDoGlownegoKlucza).replaceAll(":", "").replaceAll("\"", "");
                String bezGlwonego = split[k].substring(idDoGlownegoKlucza, split[k].length());
                bezGlwonego = bezGlwonego.replaceAll("\"", "");
                //System.out.println(bezGlwonego);
                String elementyZJednejTablicy[] = bezGlwonego.split("\\},");
                // System.out.println("test");
                // System.out.println(elementyZJednejTablicy[0]);

                //Czyczesnie tablicy z klamry
                for (int i = 0; i < elementyZJednejTablicy.length; i++) {
                    elementyZJednejTablicy[i] = elementyZJednejTablicy[i].replaceAll("\\[", "").replaceAll("\\{", "").replaceAll("\\s+", "").replaceAll("}", "");

                }


                for (int i = 0; i < elementyZJednejTablicy.length; i++) {
                    //System.out.println(elementyZJednejTablicy[i]);
                    String wiersze[] = elementyZJednejTablicy[i].split(",");
                    String sKlucz = "<" + idKlucz.replaceAll("\\s+", "").replaceAll(",", "") + ">";
                    String kKlucz = "</" + idKlucz.trim().replaceAll("\\s+", "").replaceAll(",", "") + ">";
                    finalny += sKlucz + "\n";
                    for (int j = 0; j < wiersze.length; j++) {
                        //System.out.println(wiersze[j]);
                        int temp = wiersze[j].indexOf(":");
                        String key = wiersze[j].substring(0, temp).replaceAll(",", "").replaceAll(":", "").trim();
                        String val = wiersze[j].substring(temp + 1, wiersze[j].length()).replaceAll(",", "").replaceAll(":", "").trim();
                        String result = "<" + key + ">" + val + "</" + key + ">";
                        //result.replaceAll(":","");
                        //System.out.println(result);
                        finalny += result;
                    }
                    finalny += "\n" + kKlucz;

                }
            }
            //System.out.println(finalny);
            koniec = finalny;
        } catch (Exception e) {
        	koniec = cutText(w,idKlucz);
        }
        return koniec;
    }

    // public String wybierzLepsza(String w, String idKlucz){
    // 	String koniec="";
    // 	try{
    // 		koniec = cutText2(w, idKlucz);
    // 	}catch(Exception e){
    // 		koniec = cutText(w, idKlucz);
    // 	}
    // 	return koniec;
    // }
}
