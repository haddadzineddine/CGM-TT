INSERT INTO visites(uuid, patient_uuid, visite_date, type, reason, family_history)
VALUES (
    'd9e6f8ac-2d3e-4639-9940-0b6f3059db08', 
    'd7a8fcf7-30bb-4a5c-9f7c-16311e1c3cfa',  -- Refers to patient John Doe
    '2023-03-15', 
    'AT_HOME', 
    'Routine check-up', 
    'Diabetes in family'
);
INSERT INTO visites(uuid, patient_uuid, visite_date, type, reason, family_history)
VALUES (
    'b0f91e62-4ff1-4a85-9df6-bc1c0d282cf5', 
    'ae5bd2d7-9f12-4d08-98e3-9c2e5b78d3a1',  -- Refers to patient Jane Smith
    '2024-01-10', 
    'AT_HOME', 
    'Post-surgery follow-up', 
    'No known family history'
);
INSERT INTO visites(uuid, patient_uuid, visite_date, type, reason, family_history)
VALUES (
    'c8f2e57b-8f5c-421a-bbda-3cf9df08c5b2', 
    'f5e31b4f-6542-43a5-bfed-7b9a39c4b819',  -- Refers to patient Alice Johnson
    '2024-05-20', 
    'AT_HOME', 
    'Annual physical examination', 
    'Heart disease in family'
);
