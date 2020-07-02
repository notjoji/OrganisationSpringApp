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

create or replace function after_insert_or_update_employees()
returns trigger language plpgsql as $$
declare org_index int;
begin
    if new.setting_org_name is not null then
        if not exists (select 1 from organisations where name=new.setting_org_name) then
            org_index := nextval('organisation_seq');
            insert into organisations (id, name, base_id, supervisor_id)
            values (org_index, new.setting_org_name, null, new.id);
            update employees set organisation_id=org_index where id=new.id;
        end if;
    end if;
    return new;
end $$;

create trigger after_insert_or_update_employees
after insert or update on employees
for each row execute procedure after_insert_or_update_employees();

create or replace function after_insert_or_update_organisations()
returns trigger language plpgsql as $$
declare emp_index int;
begin
    if new.setting_sup_name is not null then
        if not exists (select 1 from employees where name=new.setting_sup_name) then
            emp_index := nextval('employee_seq');
            insert into employees (id, name, organisation_id, supervisor_id)
            values (emp_index, new.setting_sup_name, new.id, null);
            update organisations set supervisor_id=emp_index where id=new.id;
        end if;
    end if;
    return new;
end $$;

create trigger after_insert_or_update_organisations
after insert or update on organisations
for each row execute procedure after_insert_or_update_organisations();