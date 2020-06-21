Feature: Prepare schemas
	Scenario: Prepare schemas
		* def schemas = {}

		# ---------------------------------------------
		# Food and Nutrient
		# ---------------------------------------------

		* set schemas.nutrient =
		"""
		{
            id: '##number',
            name: '#string',
            unitName: '#string',
            amount: '#number'
		}
		"""

		* set schemas.portion =
		"""
		{
            id: '##number',
            baseUnitName: '#string',
            baseUnitAmount: '#number',
            isNutrientRefPortion: '#boolean',
            isServingSizePortion: '#boolean',
            description: '##string',
            displayUnitName: '##string',
            displayUnitAmount: '##number'
		}
		"""

		* set schemas.food =
		"""
		{
			id: '##number',
			fdcId: '##string',
			description: '#string',
			brandOwner: '##string',
			ingredients: '##string',
			nutrients: '##[] schemas.nutrient',
			portions: '##[] schemas.portion'
		}
		"""

		# ---------------------------------------------
		# Combo Food
		# ---------------------------------------------

		* set schemas.comboFoodFoodAmount =
		"""
		{
			id: '##number',
			food: '#(schemas.food)',
			baseUnitAmount: '#number',
		}
		"""

		* set schemas.comboFoodPortion =
		"""
		{
			id: '##number',
			baseUnitName: '#string',
            baseUnitAmount: '#number',
            isFoodAmountRefPortion: '#boolean',
            isServingSizePortion: '#boolean',
            description: '##string',
            displayUnitName: '##string',
            displayUnitAmount: '##number'
		}
		"""

		* set schemas.comboFood =
		"""
		{
			id: '##number',
			description: '#string',
			foodAmounts: '##[] schemas.comboFoodFoodAmount',
			portions: '##[] schemas.comboFoodPortion'
		}
		"""