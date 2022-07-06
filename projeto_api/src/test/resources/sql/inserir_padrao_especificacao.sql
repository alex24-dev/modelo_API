INSERT INTO snba.padrao_especificacao(
	id, nome_padrao_especificacao, subclasse_id, situacao_id, ctr_dth_inc, ctr_dth_atu, ctr_usu_inc, ctr_usu_atu, excluido)
    VALUES (1, 'Fogo', 2, 1, now(), now(), 'snba_application', 'snba_application', false),
           (2, 'Utilitario',2, 1, now(), now(), 'snba_application', 'snba_application', false),
           (3, 'Frutas',3, 1, now(), now(), 'snba_application', 'snba_application', false);

SELECT setval(' snba.sq_padrao_especificacao', 4);