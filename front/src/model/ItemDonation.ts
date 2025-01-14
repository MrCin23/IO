export class ItemDonation{
    id: number;
    donorId: number;
    needId: number;
    needName: string;
    date: Date;
    itemName: string;
    category: string;
    description: string;
    quantity: number;
    status: string;

    constructor(
        id: number,
        donorId: number,
        needId: number,
        needName: string,
        date: Date,
        itemName: string,
        category: string,
        description: string,
        quantity: number,
        status: string) {

        this.id = id
        this.donorId = donorId
        this.needId = needId
        this.needName = needName
        this.date = date
        this.itemName = itemName
        this.category = category
        this.description = description
        this.quantity = quantity
        this.status = status
    }
}