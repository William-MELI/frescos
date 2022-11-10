package com.meli.frescos.service;

import com.meli.frescos.exception.BuyerNotFoundException;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.repository.BuyerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BuyerServiceTest {

    @InjectMocks
    private BuyerService service;

    @Mock
    private BuyerRepository repository;

    @Test
    @DisplayName("Create a new Buyer successfully")
    void saveSeller_returnsCreatedSeller_whenSuccess() {
        String name = "Buyer";
        String cpf = "12345678900";

        BDDMockito.when(repository.save(ArgumentMatchers.any(BuyerModel.class)))
                .thenReturn(new BuyerModel(1L, name, cpf));

        BuyerModel newBuyer = service.save(new BuyerModel(name, cpf));

        assertThat(newBuyer).isNotNull();
        assertThat(newBuyer.getId()).isPositive();
        assertEquals(name, newBuyer.getName());
        assertEquals(cpf, newBuyer.getCpf());

    }

    @Test
    @DisplayName("Return all Buyer")
    void findAll_returnAllSeller_whenSuccess() {
        List<BuyerModel> buyerModelList = new ArrayList<>();
        buyerModelList.add(new BuyerModel("Buyer", "12345678900"));

        BDDMockito.when(repository.findAll())
                .thenReturn(buyerModelList);

        List<BuyerModel> buyerList = service.findAll();

        assertThat(buyerList).isNotNull();
        assertThat(buyerList).isEqualTo(buyerModelList);
    }

    @Test
    @DisplayName("Return a Buyer by id")
    void findById_returnBuyer_whenSucess() throws BuyerNotFoundException {
        Long id = 1L;
        String name = "Buyer";
        String cpf = "12345678900";
        BuyerModel buyer = new BuyerModel(id, name, cpf);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(buyer));

        BuyerModel buyerTest = service.findById(id);

        assertThat(buyerTest).isNotNull();
        assertThat(buyerTest).isEqualTo(buyer);
    }

    @Test
    @DisplayName("Throw exception when ID is not found.")
    void findByIdSeller_returnBuyerByIdNotFoundException_whenInvalidId() {
        assertThrows(BuyerNotFoundException.class, () -> {
            BuyerModel buyerModel = service.findById(ArgumentMatchers.anyLong());
        });
    }

    @Test
    @DisplayName("Return updated Buyer")
    void updateSeller_returnBuyerUpdated_whenSuccess() throws BuyerNotFoundException {
        Long id = 1L;
        String name = "Buyer";
        String cpf = "12345678900";

        BuyerModel buyer = new BuyerModel(id, name, cpf);

        BDDMockito.when(repository.save(ArgumentMatchers.any(BuyerModel.class)))
                .thenReturn(buyer);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(buyer));

        String newName = "Vendedor teste";
        BuyerModel buyerTest = new BuyerModel(id, newName, cpf);

        service.update(buyerTest, id);

        assertThat(buyerTest).isNotNull();
        assertEquals(id, buyerTest.getId());
        assertEquals(newName, buyerTest.getName());
        assertEquals(cpf, buyerTest.getCpf());
    }

    @Test
    @DisplayName("Delete Buyer")
    void deleteById_notReturn_whenSuccess() {
        Long id = 1L;
        String name = "Buyer";
        String cpf = "12345678900";
        BuyerModel buyer = new BuyerModel(id, name, cpf);

        BDDMockito.when(repository.save(ArgumentMatchers.any(BuyerModel.class)))
                .thenReturn(buyer);

        BuyerModel buyerTest = service.save(buyer);

        service.deleteById(buyerTest.getId());

        assertThrows(BuyerNotFoundException.class, () -> {
            BuyerModel buyerModel = service.findById(id);
        });

    }

    @Test
    @DisplayName("Return a Buyer by cpf")
    void findByCpf_returnBuyer_whenSuccess() {
        Long id = 1L;
        String name = "Buyer";
        String cpf = "12345678900";
        BuyerModel buyer = new BuyerModel(id, name, cpf);

        BDDMockito.when(repository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(buyer));

        Optional<BuyerModel> buyerTest = service.findByCpf(cpf);

        assertThat(buyerTest).isNotNull();
        assertThat(buyerTest.get()).isEqualTo(buyer);
    }
}