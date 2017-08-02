package hellomaterial.paulo.ribeiro.it.projetocarros;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.List;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.domain.Carro;
import hellomaterial.paulo.ribeiro.it.domain.CarroService;

/**
 * Created by paulo on 14/07/17.
 */

public class CarroServiceTest extends AndroidTestCase {
    public void testGetCarros()throws IOException{
        List<Carro> carros = CarroService.getCarros(getContext(), R.string.esportivos,true);
        assertNotNull(carros);
        assertTrue(carros.size() == 0);

        Carro c0 = carros.get(0);
        assertEquals("Ferarri FF",c0.nome);
        assertEquals("44.532218",c0.latitude);
        assertEquals("10.864019",c0.longitude);

        Carro c9 = carros.get(9);
        assertEquals("MERCEDES-BENZ",c9.nome);
        assertEquals("-23.564224",c9.latitude);
        assertEquals("-46.653156",c9.longitude);
    }
}
