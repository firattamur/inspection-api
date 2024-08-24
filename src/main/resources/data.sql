-- Create a sequence for the questions table if it doesn't exist
CREATE SEQUENCE IF NOT EXISTS questions_id_seq START WITH 1 INCREMENT BY 1;

INSERT INTO questions (id, text, order_number, created_date)
VALUES
    (nextval('questions_id_seq'), 'Multimedyada Problem var mı?', 1, CURRENT_TIMESTAMP),
    (nextval('questions_id_seq'),  'Ruhsatta eksiklik var mı?', 2, CURRENT_TIMESTAMP),
    ( nextval('questions_id_seq'), 'Aküde eksiklik / problem var mı?', 3, CURRENT_TIMESTAMP);

-- Set the sequence to the next value after our inserts
SELECT setval('questions_id_seq', (SELECT MAX(id) FROM questions));