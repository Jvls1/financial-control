package com.jvls.financialcontrol.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jvls.financialcontrol.entities.Wallet;
import com.jvls.financialcontrol.repositories.generic.IGenericRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IWalletRepository extends IGenericRepository<Wallet> {

    Optional<Wallet> findByIdAndWalletOwnerId(UUID idWallet, UUID idUserOwner);

    @Query("""
            select w
              from Wallet w
             where 1=1 
               and w.walletOwner.id = :idUser
                or w.walletCollaborator.id = :idUser
            """)
    Page<Wallet> findAllByWalletOwnerIdOrWalletCollaboratorId(Pageable page, UUID idUser);

    List<Wallet> findAllByWalletOwnerId(UUID idUserOwner);

    @Transactional
    @Modifying
    @Query("""
            update Wallet w 
               set w.walletCollaborator = null
             where w.id = :idWallet 
               and w.walletCollaborator.id = :idUserCollaborator
             """)
    void removeWalletCollaboratorByWalletIdAndUserId(UUID idWallet, UUID idUserCollaborator);

    @Transactional
    @Modifying
    @Query("""
             update Wallet w 
               set w.timeRemoved = CURRENT_TIMESTAMP() 
             where w.id = :idWallet 
             """)
    void deleteByWalletId(UUID idWallet);

}
