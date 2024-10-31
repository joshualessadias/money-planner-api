INSERT INTO payment_method (created_at, last_updated_at, name, code)
VALUES (NOW(), NOW(), N'Cartão de Crédito', N'CRTCRD'),
       (NOW(), NOW(), N'Cartão de Débito', N'CRTDBT'),
       (NOW(), NOW(), N'Boleto', N'BLT'),
       (NOW(), NOW(), N'Pix', N'PIX'),
       (NOW(), NOW(), N'Dinheiro', N'DIN');