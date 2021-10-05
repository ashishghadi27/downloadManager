package root;

import root.util.InitiateDownload;

public class Main {

    static String url1 = "http://212.183.159.230/1GB.zip";
    static String url2 = "https://firebasestorage.googleapis.com/v0/b/studlars-80f0d.appspot.com/o/posts%2FOpdh.gif?alt=media&token=4d7f084c-86b7-428f-8aed-52e38570feac";
    static String url3 = "https://media.istockphoto.com/photos/group-of-people-activists-protesting-on-streets-march-and-concept-picture-id1283353642?b=1&k=20&m=1283353642&s=170667a&w=0&h=xExqOUjvRK36J-7bCd4OwdLTMiyVD93itxIN8ekJKY4=";
    static String url4 = "https://paglasongs.com/files/download/id/1389";
    static  String url5 = "https://releases.ubuntu.com/20.04.3/ubuntu-20.04.3-desktop-amd64.iso?_ga=2.166648635.443061836.1633170171-362602423.1633170171";
    static String url6 = "https://mail.google.com/mail/u/0?ui=2&ik=de881f476a&attid=0.1&permmsgid=msg-a:r-1654414833573226900&th=17c2830be0ae5fc8&view=att&disp=inline&realattid=17c283093c4967a4a901";

    public static void main(String[] args) {
        System.out.println("Starting Download");
        System.out.println(System.currentTimeMillis());
        InitiateDownload download = new InitiateDownload(url5);
    }

    //First: -> 11.876
    //Second: -> 9.465
}
