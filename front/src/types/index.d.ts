export interface Resource {
  resourceId: number;
  resourceName: string;
  resourceType: string;
  resourceQuantity: number;
  resourceStatus: string;
  warehouseId: number;
}

export interface Warehouse {
  warehouseId: number;
  warehouseName: string;
  location: string;
}