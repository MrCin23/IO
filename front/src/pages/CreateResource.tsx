import ResourceForm from "../components/layouts/resources/ResourceForm.tsx";
import {useTranslation} from "react-i18next";

export const CreateResource = () => {
  const { t } = useTranslation();
  return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50">
        <div className="max-w-lg w-full bg-white shadow-md rounded p-6">
          <div className="text-center mb-6">
            <h1 className="text-2xl font-semibold">{t("createResource")}</h1>
            <p className="text-sm text-gray-600">
              {t("blablaResource")}
            </p>
          </div>
          <div className="mb-6">
            <ResourceForm />
          </div>
        </div>
      </div>
  );
};
