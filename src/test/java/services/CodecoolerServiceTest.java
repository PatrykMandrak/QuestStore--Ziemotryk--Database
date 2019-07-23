package services;

import databaseAccess.ArtifactsDAO;
import databaseAccess.BackpacksDAO;
import databaseAccess.CodecoolersDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CodecoolerServiceTest {
    private CodecoolerService codecoolerService;


    @BeforeEach
    void setup(){

        codecoolerService = new CodecoolerService(new CodecoolersDAO(), new ArtifactsDAO(), new BackpacksDAO());
    }


    @Test
    void getAccountIdBy_codecoolerCoinBiggerThanArtifPrize_True(){
        int codecoolerCoolcoin = 10;
        int artifactPrize = 5;

        assertTrue(codecoolerService.enoughCoins(codecoolerCoolcoin, artifactPrize));
    }

    @Test
    void getAccountIdBy_codecoolerCoinEqualThanArtifPrize_True(){
        int codecoolerCoolcoin = 10;
        int artifactPrize = 10;

        assertTrue(codecoolerService.enoughCoins(codecoolerCoolcoin, artifactPrize));
    }


    @Test
    void getAccountIdBy_codecoolerCoinLessThanArtifPrize_False(){
        int codecoolerCoolcoin = 5;
        int artifactPrize = 10;

        assertFalse(codecoolerService.enoughCoins(codecoolerCoolcoin, artifactPrize));
    }


}