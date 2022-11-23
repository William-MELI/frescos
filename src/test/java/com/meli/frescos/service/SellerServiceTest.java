package com.meli.frescos.service;

import com.meli.frescos.exception.CpfDuplicateException;
import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.model.UserProfileEnum;
import com.meli.frescos.repository.SellerRepository;
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
class SellerServiceTest {

    @InjectMocks
    private SellerService service;

    @Mock
    private SellerRepository repository;

    @Test
    @DisplayName("Create a new Seller successfully")
    void saveSeller_returnsCreatedSeller_whenSuccess() {
        String name = "Vendedor 1";
        String cpf = "12345678900";
        double rating = 4.2;

        BDDMockito.when(repository.save(ArgumentMatchers.any(SellerModel.class)))
                .thenReturn(new SellerModel(1L, name, cpf, rating, UserProfileEnum.Seller));

        SellerModel newSeller = service.save(new SellerModel(name, cpf, rating, UserProfileEnum.Seller));

        assertThat(newSeller).isNotNull();
        assertThat(newSeller.getId()).isPositive();
        assertEquals(name, newSeller.getName());
        assertEquals(cpf, newSeller.getCpf());
        assertEquals(rating, newSeller.getRating());

    }

    @Test
    @DisplayName("Throw exception when CPF already exists.")
    void save_throwsCpfDuplicateException_whenCreatingANewSeller() {
        SellerModel dumbSeller = new SellerModel(1L, "", "", Double.valueOf(0.0), UserProfileEnum.Seller);

        BDDMockito.when(repository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(dumbSeller));

        assertThrows(CpfDuplicateException.class, () -> {
            service.save(dumbSeller);
        });
    }

    @Test
    @DisplayName("Return all storage Seller")
    void findAll_returnAllSeller_whenSuccess() {
        List<SellerModel> sellerModelList = new ArrayList<>();
        sellerModelList.add(new SellerModel("Vendedor 1", "12345678900", 4.0, UserProfileEnum.Seller));

        BDDMockito.when(repository.findAll())
                .thenReturn(sellerModelList);

        List<SellerModel> sellerList = service.getAll();

        assertThat(sellerList).isNotNull();
        assertThat(sellerList).isEqualTo(sellerModelList);
    }

    @Test
    @DisplayName("Return a Seller by id")
    void findByIdSeller_returnSeller_whenSucess() {
        Long id = 1L;
        String name = "Vendedor 1";
        String cpf = "12345678900";
        double rating = 4.2;
        SellerModel seller = new SellerModel(id, name, cpf, rating, UserProfileEnum.Seller);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(seller));

        SellerModel sellerTest = service.getById(id);

        assertThat(sellerTest).isNotNull();
        assertThat(sellerTest).isEqualTo(seller);
    }

    @Test
    @DisplayName("Throw exception when ID is not found.")
    void findByIdSeller_returnSellerByIdNotFoundException_whenInvalidId() {
        assertThrows(SellerByIdNotFoundException.class, () -> {
            SellerModel sellerModel = service.getById(ArgumentMatchers.anyLong());
        });
    }


    @Test
    @DisplayName("Return updated Seller")
    void updateSeller_returnSellerUpdated_whenSuccess() {
        Long id = 1L;
        String name = "Vendedor 1";
        String cpf = "12345678900";
        double rating = 4.2;

        SellerModel seller = new SellerModel(id, name, cpf, rating, UserProfileEnum.Seller);

        BDDMockito.when(repository.save(ArgumentMatchers.any(SellerModel.class)))
                .thenReturn(seller);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(seller));

        String newName = "Vendedor teste";
        SellerModel sellerTest = new SellerModel(id, newName, cpf, rating, UserProfileEnum.Seller);

        service.update(sellerTest, id);

        assertThat(sellerTest).isNotNull();
        assertEquals(id, sellerTest.getId());
        assertEquals(newName, sellerTest.getName());
        assertEquals(cpf, sellerTest.getCpf());
        assertEquals(rating, sellerTest.getRating());
    }

    @Test
    @DisplayName("Delete Seller")
    void deleteById_notReturn_whenSuccess() {
        Long id = 1L;
        String name = "Vendedor 1";
        String cpf = "12345678900";
        double rating = 4.2;
        SellerModel seller = new SellerModel(id, name, cpf, rating, UserProfileEnum.Seller);

        BDDMockito.when(repository.save(ArgumentMatchers.any(SellerModel.class)))
                .thenReturn(seller);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(seller));

        SellerModel sellerTest = service.save(seller);

        service.deleteById(sellerTest.getId());

    }

    @Test
    @DisplayName("Delete Seller throws exception when does not exists")
    void deleteById_throwsSellerByIdNotFoundException_whenSellerDoesNotExists() {
        Long id = 1L;


        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new SellerByIdNotFoundException(id));


        assertThrows(
                SellerByIdNotFoundException.class,
                () -> {
                    service.deleteById(id);
                });



    }

    @Test
    @DisplayName("Return a Seller by cpf")
    void findByCpf_returnSeller_whenSuccess() {
        Long id = 1L;
        String name = "Vendedor 1";
        String cpf = "12345678900";
        double rating = 4.2;
        SellerModel seller = new SellerModel(id, name, cpf, rating, UserProfileEnum.Seller);

        BDDMockito.when(repository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(seller));

        Optional<SellerModel> sellerTest = service.getByCpf(cpf);

        assertThat(sellerTest).isNotNull();
        assertThat(sellerTest.get()).isEqualTo(seller);
    }
}