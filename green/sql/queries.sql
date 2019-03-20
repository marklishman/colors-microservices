

SELECT grp_name, itm_name, cat_name, dat_value
FROM "group"
       JOIN "item" USING (grp_id)
       JOIN "data" USING (itm_id)
       JOIN "category" USING (cat_id)
ORDER by 1,2,3;
