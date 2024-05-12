INSERT INTO payment_method (created_at, last_updated_at, name, code)
VALUES (GETDATE(), GETDATE(), N'Cartão de Crédito', N'CRTCRD'),
       (GETDATE(), GETDATE(), N'Cartão de Débito', N'CRTDBT'),
       (GETDATE(), GETDATE(), N'Boleto', N'BLT'),
       (GETDATE(), GETDATE(), N'Pix', N'PIX'),
       (GETDATE(), GETDATE(), N'Dinheiro', N'DIN');