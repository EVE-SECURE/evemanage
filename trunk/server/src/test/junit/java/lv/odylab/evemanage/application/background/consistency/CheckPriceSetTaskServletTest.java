package lv.odylab.evemanage.application.background.consistency;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckPriceSetTaskServletTest {
    @Mock
    private PriceSetDao priceSetDao;
    @Mock
    private CharacterDao characterDao;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    private CheckPriceSetTaskServlet checkPriceSetTaskServlet;

    @Before
    public void setUp() {
        checkPriceSetTaskServlet = new CheckPriceSetTaskServlet(priceSetDao, characterDao);
    }

    @Test
    public void testDoPost() throws Exception {
        PriceSet priceSet = new PriceSet();
        CharacterInfo attachedCharacterInfo = new CharacterInfo();
        attachedCharacterInfo.setId(2L);
        lv.odylab.evemanage.domain.eve.Character character = new Character();
        character.setCharacterID(3L);
        character.setName("characterName");
        character.setCorporationID(4L);
        character.setCorporationName("corporationName");
        character.setCorporationTicker("corporationTicker");
        character.setAllianceID(5L);
        character.setAllianceName("allianceName");
        priceSet.setAttachedCharacterInfo(attachedCharacterInfo);
        when(httpServletRequest.getParameter("priceSetID")).thenReturn("1");
        when(priceSetDao.get(new Key<PriceSet>(PriceSet.class, 1))).thenReturn(priceSet);
        when(characterDao.get(new Key<Character>(Character.class, 2))).thenReturn(character);
        checkPriceSetTaskServlet.doPost(httpServletRequest, httpServletResponse);
        verify(priceSetDao).putWithoutChecks(priceSet);
        assertEquals(Long.valueOf(3), attachedCharacterInfo.getCharacterID());
        assertEquals("characterName", attachedCharacterInfo.getName());
        assertEquals(Long.valueOf(4), attachedCharacterInfo.getCorporationID());
        assertEquals("corporationName", attachedCharacterInfo.getCorporationName());
        assertEquals("corporationTicker", attachedCharacterInfo.getCorporationTicker());
        assertEquals(Long.valueOf(5), attachedCharacterInfo.getAllianceID());
        assertEquals("allianceName", attachedCharacterInfo.getAllianceName());
    }

    @Test
    public void testDoPost_WrongCharacter() throws Exception {
        PriceSet priceSet = new PriceSet();
        CharacterInfo attachedCharacterInfo = new CharacterInfo();
        attachedCharacterInfo.setId(2L);
        priceSet.setAttachedCharacterInfo(attachedCharacterInfo);
        when(httpServletRequest.getParameter("priceSetID")).thenReturn("1");
        when(priceSetDao.get(new Key<PriceSet>(PriceSet.class, 1))).thenReturn(priceSet);
        when(characterDao.get(new Key<Character>(Character.class, 2))).thenThrow(new NotFoundException(null));
        checkPriceSetTaskServlet.doPost(httpServletRequest, httpServletResponse);
        verify(priceSetDao).putWithoutChecks(priceSet);
        assertNull(priceSet.getAttachedCharacterInfo());
    }

    @Test
    public void testDoPost_NoCharacter() throws Exception {
        PriceSet priceSet = new PriceSet();
        when(httpServletRequest.getParameter("priceSetID")).thenReturn("1");
        when(priceSetDao.get(new Key<PriceSet>(PriceSet.class, 1))).thenReturn(priceSet);
        checkPriceSetTaskServlet.doPost(httpServletRequest, httpServletResponse);
        verify(priceSetDao, never()).putWithoutChecks(priceSet);
    }

    @Test
    public void testDoPost_Exception() throws Exception {
        when(httpServletRequest.getParameter("userID")).thenReturn("1");
        doThrow(new RuntimeException()).when(priceSetDao).get(new Key<PriceSet>(PriceSet.class, 1));
        checkPriceSetTaskServlet.doPost(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDoPost_Throwable() throws Exception {
        checkPriceSetTaskServlet.doPost(httpServletRequest, httpServletResponse);
    }
}
