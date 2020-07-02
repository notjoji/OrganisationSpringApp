create or replace function before_delete_employees()
returns trigger language plpgsql as $$
begin
    if exists (select 1 from employees where supervisor_id in
        (select id from employees where id=old.id)) then
            raise exception 'cant delete a supervisor';
    end if;
    return old;
end $$;

create trigger before_delete_employees
before delete on employees
for each row execute procedure before_delete_employees();

create or replace function before_delete_organisations()
returns trigger language plpgsql as $$
begin
    if exists (select 1 from organisations where base_id in
        (select id from organisations where id=old.id)) then
            raise exception 'cant delete a base organisation';
    end if;
    return old;
end $$;

create trigger before_delete_organisations
before delete on organisations
for each row execute procedure before_delete_organisations();