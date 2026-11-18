package com.cadastrodeproduto.api.repository;

import com.cadastrodeproduto.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
