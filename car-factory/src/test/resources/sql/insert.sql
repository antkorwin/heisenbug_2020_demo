insert into engine (id,
                    fuel_type,
                    horse_power,
                    volume)
values ('fe83dfbb-2e59-437b-b061-fe8cdb928847',
        'GASOLINE',
        396,
        4.7),
       ('d1ef755d-f593-4f90-98f0-e2f4e4b24faa',
        'DIESEL',
        160,
        4.2);

insert into car (id,
                 name,
                 year,
                 engine_id)
values ('009f21e0-6f1c-4fc5-baa9-5893303caf82',
        'Mustang',
        1970,
        'fe83dfbb-2e59-437b-b061-fe8cdb928847');