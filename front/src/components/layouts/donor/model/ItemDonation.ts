export class ItemDonation{
    id: number;
    donorId: number;
    needId: number;
    needDescription: string;
    donationDate: Date;
    itemName: string;
    category: string;
    description: string;
    resourceQuantity: number;
    resourceStatus: string;

    constructor(
        id: number,
        donorId: number,
        needId: number,
        needDescription: string,
        donationDate: Date,
        itemName: string,
        category: string,
        description: string,
        resourceQuantity: number,
        resourceStatus: string) {

        this.id = id
        this.donorId = donorId
        this.needId = needId
        this.needDescription = needDescription
        this.donationDate = donationDate
        this.itemName = itemName
        this.category = category
        this.description = description
        this.resourceQuantity = resourceQuantity
        this.resourceStatus = resourceStatus
    }
}