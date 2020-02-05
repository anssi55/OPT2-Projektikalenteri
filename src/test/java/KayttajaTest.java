
import com.mycompany.projektikalenteri.Kalenterimerkinta;
import com.mycompany.projektikalenteri.Kayttaja;
import com.mycompany.projektikalenteri.Projekti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author henri
 */
public class KayttajaTest {
    
    private Kayttaja kayttaja;
    
    public KayttajaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kayttaja = new Kayttaja(1, "test@test.com", "testiNimi");
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void kayttajanAlustus() {
        assertEquals("Tarkista kayttajan id:n alustus toiminto", 1, kayttaja.getId());
        assertTrue("Tarkista kayttajan kayttajatunnuksen alustus toiminto", kayttaja.getKayttajatunnus().equals("test@test.com"));
        assertTrue("Tarkista kayttajan nayttonimen alustus toiminto", kayttaja.getNayttonimi().equals("testiNimi"));
        assertEquals("Tarkista kayttajan pomonalistan alustus toiminto", 0, kayttaja.getPomona().size());
        assertEquals("Tarkista kayttajan tekijanalistan alustus toiminto", 0, kayttaja.getTekijana().size());
        assertEquals("Tarkista kayttajan merkinnatlistan alustus toiminto", 0, kayttaja.getMerkinnat().size());
    }
    
    @Test
    public void getJaSetKayttajatunnusTarkistus() {
        assertTrue("Tarkista kayttajan kayttajatunnuksen set ja get toiminto", kayttaja.getKayttajatunnus().equals(("test@test.com")));
        kayttaja.setKayttajatunnus("uusiKayttajatunnus");
        assertTrue("Tarkista kayttajan kayttajatunnuksen set ja get toiminto", kayttaja.getKayttajatunnus().equals(("uusiKayttajatunnus")));
        assertFalse("Tarkista kayttajan kayttajatunnuksen set ja get toiminto", kayttaja.getKayttajatunnus().equals(("test@test.com")));
    }
    
    @Test
    public void getJaSetSalasanaTarkistus() {
        assertTrue("Tarkista kayttajan salasanan set ja get toiminto", kayttaja.getSalasana() == null);
        kayttaja.setSalasana("uusiSala");
        assertTrue("Tarkista kayttajan salasanan set ja get toiminto", kayttaja.getSalasana().equals(("uusiSala")));
        assertFalse("Tarkista kayttajan salasanan set ja get toiminto", kayttaja.getSalasana().equals(("")));
    }
    
    @Test
    public void getJaSetNayttonimiTarkistus() {
        assertTrue("Tarkista kayttajan nayttonimen set ja get toiminto", kayttaja.getNayttonimi().equals(("testiNimi")));
        kayttaja.setNayttonimi("uusiNayttonimi");
        assertTrue("Tarkista kayttajan nayttonimen set ja get toiminto", kayttaja.getNayttonimi().equals(("uusiNayttonimi")));
        assertFalse("Tarkista kayttajan nayttonimen set ja get toiminto", kayttaja.getNayttonimi().equals(("testiNimi")));
    }
    
    @Test
    public void getJaSetIdTarkistus() {
        assertEquals("Tarkista kayttajan id:n set ja get toiminto", 1, kayttaja.getId());
        kayttaja.setId(3);
        assertEquals("Tarkista kayttajan id:n set ja get toiminto", 3, kayttaja.getId());
        assertNotEquals("Tarkista kayttajan id:n set ja get toiminto", 1, kayttaja.getId());
    }
    
    @Test
    public void luoProjektiTarkistus() {
        assertEquals("Tarkista kayttajan lisaaPomona toiminto", 0, kayttaja.getPomona().size());
        Projekti projekti = kayttaja.luoProjekti("Testiprojektin nimi");
        assertEquals("Tarkista kayttajan lisaaPomona toiminto", 1, kayttaja.getPomona().size());
        assertTrue("Tarkista kayttajan lisaaPomona toiminto", kayttaja.getPomona().get(0).getPomo().equals(kayttaja.getNayttonimi()));
        assertTrue("Tarkista kayttajan lisaaPomona toiminto", projekti.getNimi().equals("Testiprojektin nimi"));
    }
    
    @Test
    public void lisaaPomonaTarkistus() {
        assertEquals("Tarkista kayttajan lisaaPomona toiminto", 0, kayttaja.getPomona().size());
        kayttaja.lisaaPomona(new Projekti("Testiprojektin nimi", kayttaja));
        assertEquals("Tarkista kayttajan lisaaPomona toiminto", 1, kayttaja.getPomona().size());
        assertTrue("Tarkista kayttajan lisaaPomona toiminto", kayttaja.getPomona().get(0).getPomo().equals(kayttaja.getNayttonimi()));
    }
    
    @Test
    public void lisaaTekijanaTarkistus() {
        assertEquals("Tarkista kayttajan lisaaTekijana toiminto", 0, kayttaja.getTekijana().size());
        kayttaja.lisaaTekijana(new Projekti("Testiprojektin nimi", kayttaja));
        assertEquals("Tarkista kayttajan lisaaTekijana toiminto", 1, kayttaja.getTekijana().size());
        assertTrue("Tarkista kayttajan lisaaTekijana toiminto", kayttaja.getTekijana().get(0).getNimi().equals("Testiprojektin nimi"));   
    }
    
    @Test
    public void lisaaMerkinnatTarkistus() {
        assertEquals("Tarkista kayttajan lisaaMerkinnat toiminto", 0, kayttaja.getMerkinnat().size());
        kayttaja.lisaaMerkinnat(new Kalenterimerkinta(kayttaja.getNayttonimi()));
        assertEquals("Tarkista kayttajan lisaaMerkinnat toiminto", 1, kayttaja.getMerkinnat().size());
        assertTrue("Tarkista kayttajan lisaaMerkinnat toiminto", kayttaja.getMerkinnat().get(0).getKayttaja().equals(kayttaja.getNayttonimi()));
    }
}