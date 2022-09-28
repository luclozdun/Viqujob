package com.viqujob.jobagapi.enterprise.sector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import com.viqujob.jobagapi.enterprise.domain.model.Sector;
import com.viqujob.jobagapi.enterprise.domain.repository.SectorRepository;
import com.viqujob.jobagapi.enterprise.service.SectorServiceImpl;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SectorServiceImplTest {
    
    @Spy
    private SectorRepository sectorRepository;

    @InjectMocks
    private SectorServiceImpl sectorServiceImpl;
    
    Sector sector;

    @BeforeEach
    public void setup(){
        sector = new Sector();
        sector.setDescription("sample text");
        sector.setId(69L);
        sector.setName("Sector 7");
    }

    @Test
    @DisplayName("When Find By Id Then Return Sector")
    public void WhenFindByIdThenReturnSector(){
        //arrange
        when(sectorRepository.findById(69L)).thenReturn(Optional.of(sector));

        //act
        Sector response = sectorServiceImpl.getSectorById(69L);

        //assert
        assertEquals(69L, response.getId());
    }

    @Test
    @DisplayName("When Find By Id But Section Not Exist")
    public void WhenFindByIdButSectionNotExist(){
        //arrange
        when(sectorRepository.findById(69L)).thenReturn(Optional.of(sector));

        //act
        String message = "Resource Sector not found for Id with value 1337";
        Throwable exception = catchThrowable(()->{
            sectorServiceImpl.getSectorById(1337L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Save A Sector")
    public void WhenSaveASector(){
        //arrange
        when(sectorRepository.save(sector)).thenReturn(sector);

        //act
        Sector response = sectorServiceImpl.createSector(sector);
        
        //assert
        assertEquals(69L, response.getId());
    }

    @Test
    @DisplayName("When Save A Sector But Sector Not Exist")
    public void WhenSaveASectorButSectorNotExist(){
        //arrange
        when(sectorRepository.findById(69L)).thenReturn(Optional.of(sector));

        //act
        String message = "Resource Sector not found for Id with value 1337";
        Throwable exception = catchThrowable(()->{
            sectorServiceImpl.getSectorById(1337L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete A Sector")
    public void WhenDeleteASector(){
        //arrange
        doNothing().when(sectorRepository).delete(sector);
        when(sectorRepository.findById(69L)).thenReturn(Optional.of(sector));
        
        //act
        HttpStatus response = sectorServiceImpl.deleteSector(69L).getStatusCode();
        //assert
        assertEquals(HttpStatus.OK,response.OK);
    }

    @Test
    @DisplayName("When Delete A Sector But Sector Not Exist")
    public void WhenDeleteASectorButSectorNotExist(){
        //arrange
        when(sectorRepository.findById(69L)).thenReturn(Optional.of(sector));

        //act
        String message = "Resource Sector not found for Id with value 1337";
        Throwable exception = catchThrowable(()->{
            sectorServiceImpl.getSectorById(1337L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update A Sector")
    public void WhenUpdateASector(){
        //arrange
        when(sectorRepository.save(sector)).thenReturn(sector);
        when(sectorRepository.findById(69L)).thenReturn(Optional.of(sector));
        Sector sectorRequest = new Sector();
        sectorRequest.setDescription("sample text2");
        sectorRequest.setName("Sector 420");
        //act
        Sector response = sectorServiceImpl.updateSector(69L,sectorRequest);
        //assert
        assertEquals(69L, response.getId());
    }

    @Test
    @DisplayName("When Update A Sector But Sector Not Exist")
    public void WhenUpdateASectorButSectorNotExist(){
        //arrange
        when(sectorRepository.findById(69L)).thenReturn(Optional.of(sector));

        //act
        String message = "Resource Sector not found for Id with value 1337";
        Throwable exception = catchThrowable(()->{
            sectorServiceImpl.getSectorById(1337L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }
}