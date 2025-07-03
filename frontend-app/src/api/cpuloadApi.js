import http from "@/http";

const getCpuLoadApi = () => {
  return http.get("metrics/cpuload");
};

export{
    getCpuLoadApi
}
