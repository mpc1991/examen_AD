package net.porcel.examen_presencial_ad_plantilla.Auxiliars;
public class U5_tractarValors_ORDB {

    public static String[] parseValue(String value) {

        //Si el darrer atribut és nul tendrem ,) ho substituim per ,null)
        value = value.replaceAll(",\\)", ",null)");

        //si el primer atribut és nul tendrem (, ho substituim per (null,
        value = value.replaceAll("\\(,", "(null,");

        //Si un altre camp és null tendrem ,, ho substituim per ,null,
        value = value.replaceAll(",,", ",null,");

        //Llevam parèntesis
        String valor = value.substring(1, value.length() - 1);

        //si hi ha atributs amb comes dins del text ens complica el procés, 
        //substituirem aquestes comes per un caràcter no imprimible.
        valor = tractaComes(valor);

        //Separam els diferents valors per ,
        String[] atributs = valor.split(",");

        //Eliminar " inicial i final si n'hi ha. Assignam nuls i recuperam les comes.
        for (int i = 0; i < atributs.length; i++) {
            if ("null".equalsIgnoreCase(atributs[i])) {
                atributs[i] = null;
            } else if (atributs[i].charAt(0) == '"'
                    && atributs[i].charAt(atributs[i].length() - 1) == '"') {
                atributs[i] = atributs[i].substring(1, atributs[i].length() - 1);
                atributs[i] = recuperaComes(atributs[i]);
            }
        }
        return atributs;
    }

    private static String tractaComes(String s) {
        boolean obertes = false;
        //Converteix la cadena a array de caràcters.
        char[] caracters = s.toCharArray();
        //Totes les , que estiguin entre " " les substitueix per el caracter 7
        for (int i = 0; i < caracters.length; i++) {
            if (caracters[i] == '"') {
                obertes = !obertes;
            } else if (caracters[i] == ',' && obertes) {
                caracters[i] = (char) 7;
            }
        }
        //Torna una cadena creada a partir de l'array de caràcters.
        return new String(caracters);
    }

    private static String recuperaComes(String s) {
        // Reemplazamos el carácter no imprimible por una coma.
        return s.replace((char) 7, ',');
    }

}

