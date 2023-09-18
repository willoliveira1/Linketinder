export default class Certificate {

    id: number = 0;
    title: string = "";
    duration: string = "";

    constructor(id: number, title: string, duration: string) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

}
