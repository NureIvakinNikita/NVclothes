CREATE TABLE project.post_office (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        city_id INTEGER REFERENCES project.city(id) ON DELETE CASCADE
);

INSERT INTO project.post_office (name, city_id)
VALUES
    ('Nova Poshta №.21, Kharkiv, str. Akademika Proskura, 1', 1),
    ('Nova Poshta №.1, Kharkiv, str. Poleva, 67', 1),
    ('Nova Poshta №.2, Kharkiv, 54a Heroiv Kharkiv avenue (formerly Moskovskiy).', 1),
    ('Nova Poshta №.3, Kharkiv, str. Tyurinska (early Yakira), 124', 1),
    ('Nova Poshta №.4, Kharkiv, str. Dostoevsky, 5', 1),
    ('Nova Poshta №.1, Kyiv, str. Pirogovsky Shlyach, 135', 2),
    ('Nova Poshta №.2, Kyiv, str. Bogatyrska, 11', 2),
    ('Nova Poshta №.3, Kyiv, str. Kalachivska, 13 (Stara Darnytsia)', 2),
    ('Nova Poshta №.4, Kyiv, str. Verhovynna, 69', 2),
    ('Nova Poshta №.5, Kyiv, str. Fedorova, 32 (Olympiyska)', 2);