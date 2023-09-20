export const regexText: RegExp = /^(?!^\s+)[A-Za-zÀ-ú0-9\.&,\s-]{1,50}$/;

export const regexEmail: RegExp = /^[\w\.-]{2,}@\w{3,}\.\w{2,6}(\.\w{2,3})?$/i;

export const regexCnpj: RegExp = /^\d{2}(\.)?\d{3}(\.)?\d{3}(\/)?\d{4}(-)?\d{2}$/;

export const regexCpf: RegExp = /^\d{3}(\.)?\d{3}(\.)?\d{3}(-)?\d{2}$/;

export const regexCity: RegExp = /^(?!^\s+)([A-Za-zÀ-ú\s]{1,16})*$/i;

export const regexCep: RegExp = /^\d{2}(\.)?\d{3}(-)?\d{3}$/;

export const regexDescription: RegExp = /^(?!^\s+)(\w|\s|\.|,|&)+$/;
